package by.bsuir.wt_lab.parser;

import by.bsuir.wt_lab.entity.*;
import by.bsuir.wt_lab.tagEnum.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.util.ArrayList;

public class SAXHandler extends DefaultHandler {

    private static final Logger logger = LogManager.getLogger();
    private final static String xmlFile = "src/main/resources/data.xml";

    private ArrayList<Attraction> attractionList = new ArrayList<Attraction>();
    private Attraction attraction;
    private ArrayList<ServiceBuilding> serviceBuildingList = new ArrayList<ServiceBuilding>();
    private ServiceBuilding serviceBuilding;
    private ArrayList<Territory> territoryList = new ArrayList<Territory>();;
    private Territory territory;

    private StringBuilder text;
    private EntityTag entityTag = EntityTag.NONE;

    public AmusementPark getAmusementPark() {
        AmusementPark amusementPark = new AmusementPark();
        amusementPark.setTerritories(territoryList);
        amusementPark.setAttractions(attractionList);
        amusementPark.setServiceBuildings(serviceBuildingList);
        logger.info("Parse territory " + territoryList.size());
        return amusementPark;
    }

    public ArrayList<Territory> getDepartmentList() {
        return territoryList;
    }

    public ArrayList<Attraction> getDoctorList() {
        return attractionList;
    }

    public ArrayList<ServiceBuilding> getNurseList() {
        return serviceBuildingList;
    }

    private static SAXHandler instance = null;

    public static SAXHandler getInstance(String path) {
        if (instance == null) {
            instance = new SAXHandler();
            try {
                XMLReader xmlReader = XMLReaderFactory.createXMLReader();
                xmlReader.setContentHandler(instance);

                xmlReader.parse(new InputSource(path));
            } catch (SAXException | IOException | NullPointerException e) {
                logger.error(e.getMessage());
            }
        }
        return instance;
    }

    @Override
    public void startDocument() throws SAXException {
        logger.info("Start parsing...");
    }

    @Override
    public void endDocument() throws SAXException {
        logger.info("Finish parsing...");
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        text = new StringBuilder();
        EntityTag rootTag = EntityTag.NONE;
        try {
            rootTag = EntityTag.valueOf(qName.toUpperCase());
            entityTag = rootTag;
        } catch (IllegalArgumentException e) { }

        switch (rootTag) {
            case ATTRACTION:
                attraction = new Attraction();
                attraction.setId(Integer.parseInt(attributes.getValue("id")));
                break;
            case SERVICEBUILDING:
                serviceBuilding = new ServiceBuilding();
                serviceBuilding.setId(Integer.parseInt(attributes.getValue("id")));
                break;
            case TERRITORY:
                territory = new Territory();
                territory.setId(Integer.parseInt(attributes.getValue("id")));
                break;
        }
    }

    @Override
    public void characters(char[] buffer, int start, int length) {
        text.append(buffer, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        String qNameUpperCase = qName.toUpperCase();

        switch (entityTag) {
            case ATTRACTION: {
                AttractionTag attractionTag = AttractionTag.NONE;
                try {
                    attractionTag = AttractionTag.valueOf(qNameUpperCase);
                } catch (IllegalArgumentException e) { }

                switch (attractionTag) {
                    case NAME:
                        attraction.setName(text.toString());
                        break;
                    case BUILDPRICE:
                        attraction.setBuildPrice(Integer.parseInt(text.toString()));
                        break;
                    case TIMETOREPAIR:
                        attraction.setTimeToRepair(Long.parseLong(text.toString()));
                        break;
                    case TICKETPRICE:
                        attraction.setTicketPrice(Integer.parseInt(text.toString()));
                        break;
                    case RIDETIME:
                        attraction.setRideTime(Long.parseLong(text.toString()));
                        break;
                    case VISITORSLOVE:
                        attraction.setVisitorsLove(Byte.parseByte(text.toString()));
                        break;
                    case TYPE:
                        attraction.setType(AttractionType.valueOf(text.toString()));
                        break;
                    case TERRITORYID:
                        attraction.setTerritoryId(Integer.parseInt(text.toString()));
                        break;
                    default:
                        attractionList.add(attraction);
                        attraction = null;
                        entityTag = EntityTag.NONE;
                        break;
                }
                break;
            }
            case SERVICEBUILDING: {
                ServiceBuildingTag serviceBuildingTag = ServiceBuildingTag.NONE;
                try {
                    serviceBuildingTag = ServiceBuildingTag.valueOf(qNameUpperCase);
                } catch (IllegalArgumentException e) { }

                switch (serviceBuildingTag) {
                    case NAME:
                        serviceBuilding.setName(text.toString());
                        break;
                    case BUILDPRICE:
                        serviceBuilding.setBuildPrice(Integer.parseInt(text.toString()));
                        break;
                    case TIMETOREPAIR:
                        serviceBuilding.setTimeToRepair(Long.parseLong(text.toString()));
                        break;
                    case TERRITORYID:
                        serviceBuilding.setTerritoryId(Integer.parseInt(text.toString()));
                        break;
                    case SERVICE:
                        serviceBuilding.setService(text.toString());
                        break;
                    case PRICE:
                        serviceBuilding.setPrice(Integer.parseInt(text.toString()));
                        break;
                    default:
                        serviceBuildingList.add(serviceBuilding);
                        serviceBuilding = null;
                        entityTag = EntityTag.NONE;
                        break;
                }
                break;
            }
            case TERRITORY: {
                TerritoryTag territoryTag = TerritoryTag.NONE;
                try {
                    territoryTag = TerritoryTag.valueOf(qNameUpperCase);
                } catch (IllegalArgumentException e) { }

                switch (territoryTag) {
                    case X:
                        territory.setX(Integer.parseInt(text.toString()));
                        break;
                    case Y:
                        territory.setY(Integer.parseInt(text.toString()));
                        break;
                    case WIDTH:
                        territory.setWidth(Integer.parseInt(text.toString()));
                        break;
                    case HEIGHT:
                        territory.setHeight(Integer.parseInt(text.toString()));
                        break;
                    default:
                        territoryList.add(territory);
                        territory = null;
                        entityTag = EntityTag.NONE;
                        break;
                }
            }
        }
    }
}
