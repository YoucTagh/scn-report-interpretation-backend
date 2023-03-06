package fr.minesstetienne.ci.scnri.domain;

import org.apache.jena.graph.Graph;
import org.apache.jena.iri.Violation;
import org.apache.jena.shacl.ValidationReport;

/**
 * @author YoucTagh
 */
public class ResponseTriple {
    private Graph dataGraph;
    private String dataGraphURI;

    private Graph shapeGraph;
    private String shapeGraphURI;

    private ValidationReport report;

    public Graph getDataGraph() {
        return dataGraph;
    }

    public ResponseTriple setDataGraph(Graph dataGraph) {
        this.dataGraph = dataGraph;
        return this;
    }

    public String getDataGraphURI() {
        return dataGraphURI;
    }

    public ResponseTriple setDataGraphURI(String dataGraphURI) {
        this.dataGraphURI = dataGraphURI;
        return this;
    }

    public Graph getShapeGraph() {
        return shapeGraph;
    }

    public ResponseTriple setShapeGraph(Graph shapeGraph) {
        this.shapeGraph = shapeGraph;
        return this;
    }

    public String getShapeGraphURI() {
        return shapeGraphURI;
    }

    public ResponseTriple setShapeGraphURI(String shapeGraphURI) {
        this.shapeGraphURI = shapeGraphURI;
        return this;
    }

    public ValidationReport getReport() {
        return report;
    }

    public ResponseTriple setReport(ValidationReport report) {
        this.report = report;
        return this;
    }
}
