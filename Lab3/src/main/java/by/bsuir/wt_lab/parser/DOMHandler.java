package by.bsuir.wt_lab.parser;

import by.bsuir.wt_lab.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DOMHandler {

    private static final Logger logger = LogManager.getLogger();
    private final static String xmlFile = "src/main/resources/data.xml";

    private ArrayList<Territory> territories = new ArrayList<Territory>();
    private ArrayList<Attraction> attractions = new ArrayList<Attraction>();
    private ArrayList<ServiceBuilding> serviceBuildings = new ArrayList<ServiceBuilding>();

    private AmusementPark amusementPark = new AmusementPark();

    private static String getTextContent(Node parentNode,String childName)
    {
        NodeList nlist = parentNode.getChildNodes();
        for (int i = 0 ; i < nlist.getLength() ; i++) {
            Node n = nlist.item(i);
            String name = n.getNodeName();
            if ( name != null && name.equals(childName) )
                return n.getTextContent();
        }
        return "";
    }

    public AmusementPark parse(String path) throws IOException {
        Territory territory;
        Attraction attraction;
        ServiceBuilding serviceBuilding;

        try
        {
            File inputFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            logger.info("Start parsing...");
            NodeList nTerritories = doc.getElementsByTagName("department");
            for (int i = 0; i < nTerritories.getLength(); i++) {
                Element node = (Element)nTerritories.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    territory = new Territory();
                    territory.setX(Integer.parseInt(node.getAttribute("x")));
                    territory.setY(Integer.parseInt(node.getAttribute("y")));
                    territory.setWidth(Integer.parseInt(node.getAttribute("width")));
                    territory.setHeight(Integer.parseInt(node.getAttribute("height")));
                    territory.setId(Integer.parseInt(node.getAttribute("id")));
                    territories.add(territory);
                    territory = null;
                }
            }
            logger.info("Parse territories: " + territories.size());

            NodeList nAttractions = doc.getElementsByTagName("attraction");
            for (int i = 0; i < nAttractions.getLength(); i++) {
                Element node = (Element)nAttractions.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    attraction = new Attraction();
                    attraction.setId(Integer.parseInt(node.getAttribute("id")));
                    attraction.setName(getTextContent(node, "name"));
                    attraction.setBuildPrice(Integer.parseInt(getTextContent(node, "buildPrice")));
                    attraction.setTimeToRepair(Long.parseLong(getTextContent(node, "timeToRepair")));
                    attraction.setTicketPrice(Integer.parseInt(getTextContent(node, "ticketPrice")));
                    attraction.setRideTime(Long.parseLong(getTextContent(node, "rideTime")));
                    attraction.setVisitorsLove(Byte.parseByte(getTextContent(node, "visitorsLove")));
                    attraction.setType(AttractionType.valueOf(getTextContent(node, "type")));
                    attraction.setTerritoryId(Integer.parseInt(getTextContent(node, "territoryId")));
                    attractions.add(attraction);
                    attraction = null;
                }
            }
            logger.info("Parse attractions: " + attractions.size());

            NodeList nServiceBuildings = doc.getElementsByTagName("nurse");
            for (int i = 0; i < nServiceBuildings.getLength(); i++) {
                Element node = (Element)nServiceBuildings.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    serviceBuilding = new ServiceBuilding();
                    serviceBuilding.setId(Integer.parseInt(node.getAttribute("id")));
                    serviceBuilding.setName(getTextContent(node, "name"));
                    serviceBuilding.setBuildPrice(Integer.parseInt(getTextContent(node, "buildPrice")));
                    serviceBuilding.setTimeToRepair(Long.parseLong(getTextContent(node, "timeToRepair")));
                    serviceBuilding.setPrice(Integer.parseInt(getTextContent(node, "price")));
                    serviceBuilding.setService(getTextContent(node, "service"));
                    serviceBuilding.setTerritoryId(Integer.parseInt(getTextContent(node, "territoryId")));
                    serviceBuildings.add(serviceBuilding);
                    serviceBuilding = null;
                }
            }
            logger.info("Parse service buildings: " + serviceBuildings.size());

            amusementPark.setTerritories(territories);
            amusementPark.setAttractions(attractions);
            amusementPark.setServiceBuildings(serviceBuildings);
        } catch (Exception e) {
            logger.error("Parse error", e);
        }
        return amusementPark;
    }
}
