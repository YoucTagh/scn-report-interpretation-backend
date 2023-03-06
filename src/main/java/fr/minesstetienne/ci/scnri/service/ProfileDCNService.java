package fr.minesstetienne.ci.scnri.service;

import fr.minesstetienne.ci.scnri.domain.ResponseTriple;
import org.apache.jena.atlas.web.HttpException;
import org.apache.jena.graph.Graph;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.shacl.ValidationReport;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author YoucTagh
 */
@Service
public class ProfileDCNService {


    public ResponseEntity<String> checkConformanceOfRepresentation(String profileIri) {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(UtilService.getSemanticAcceptedMediaTypes());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseTriple bestResponse = null;

        try {
            List<String> paths = Files.list(new File(getClass().getResource("/static/data").toURI()).toPath()).map(path -> path.toUri().toString()).collect(Collectors.toList());

            for (String path : paths) {
                Graph shapesGraph = RDFDataMgr.loadGraph(profileIri);
                Shapes shapes = Shapes.parse(shapesGraph);

                Graph dataGraph = RDFDataMgr.loadGraph(path);

                if (dataGraph.isEmpty()) {
                    continue;
                }

                ValidationReport report = ShaclValidator.get().validate(shapes, dataGraph);

                if (bestResponse == null) {
                    bestResponse = new ResponseTriple()
                            .setReport(report)
                            .setDataGraph(dataGraph)
                            .setDataGraphURI(path);
                } else if (UtilService.isBetterRepresentationThan(report, bestResponse.getReport())) {
                    bestResponse
                            .setReport(report)
                            .setDataGraph(dataGraph)
                            .setDataGraphURI(path);
                }
            }

            if (bestResponse != null) {

                if (!bestResponse.getReport().getEntries().isEmpty())
                    headers.set("report", getStringGraph(bestResponse.getReport().getGraph()));

                headers.setContentType(MediaType.valueOf("text/turtle"));
                headers.set(HttpHeaders.LOCATION, bestResponse.getDataGraphURI());
                headers.set(HttpHeaders.LINK, "<" + profileIri + ">" + ";rel=\"profile\"");
                headers.setVary(Stream.of("accept-profile").collect(Collectors.toList()));

                return new ResponseEntity<>(
                        getStringGraph(bestResponse.getDataGraph()),
                        headers,
                        (bestResponse.getReport().getEntries().size() == 0) ? HttpStatus.OK : HttpStatus.PARTIAL_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (HttpException ex) {
            HttpStatus httpStatus = HttpStatus.resolve(ex.getStatusCode());
            return new ResponseEntity<>((httpStatus != null) ? httpStatus : HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private String getStringGraph(Graph graph) {
        StringWriter sw = new StringWriter();
        RDFDataMgr.write(sw, graph, Lang.TURTLE);
        return sw.toString();
    }

    public static void main(String[] args) {
        ProfileDCNService service = new ProfileDCNService();
        ResponseEntity<String> responseEntity = service.checkConformanceOfRepresentation(ProfileDCNService.class.getResource("/static/profile/shape-foaf-workat.ttl").toString());

        System.out.println("==========");
        System.out.println(responseEntity.getBody());
        System.out.println("==========");
        System.out.println(responseEntity.getStatusCode());
        System.out.println(responseEntity.getHeaders().get("report"));
        System.out.println("==========");
    }
}
