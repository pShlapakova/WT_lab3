package by.bsuir.wt_lab.parser;

import by.bsuir.wt_lab.entity.*;
import by.bsuir.wt_lab.tagEnum.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class StAXHandler {

    private static final Logger logger = LogManager.getLogger();
    private final static String xmlFile = "src/main/resources/data.xml";
    private static EntityTag entityTag = EntityTag.NONE;

    public AmusementPark parse(String path) throws FileNotFoundException {
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        AmusementPark amusementPark = new AmusementPark();
        try {
            InputStream input = new FileInputStream(path);
            XMLStreamReader reader = inputFactory.createXMLStreamReader(input);
            amusementPark = process(reader);

        } catch (XMLStreamException e) {
            logger.error("Parse error", e);
        }
        return amusementPark;
    }

    private static AmusementPark process(XMLStreamReader reader) throws XMLStreamException {
        ArrayList<Territory> territories = new ArrayList<>();
        ArrayList<Attraction> attractions = new ArrayList<>();
        ArrayList<ServiceBuilding> serviceBuildings = new ArrayList<>();

        Territory territory = null;
        Attraction attraction = null;
        ServiceBuilding serviceBuilding = null;

        AmusementPark amusementPark = null;

        String elementName = null;
        TerritoryTag territoryTag = TerritoryTag.NONE;
        AttractionTag attractionTag = AttractionTag.NONE;
        ServiceBuildingTag serviceBuildingTag = ServiceBuildingTag.NONE;

        while (reader.hasNext()) {
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    switch (entityTag) {
                        case NONE:
                            entityTag = EntityTag.valueOf(reader.getLocalName().toUpperCase());
                            amusementPark = new AmusementPark();
                            break;
                        case AMUSEMENTPARK:
                            break;
                        case TERRITORIES:
                            territory = new Territory();
                            territory.setId(Integer.parseInt(reader.getAttributeValue(null, "id")));
                            break;
                        case TERRITORY:
                            territoryTag = TerritoryTag.valueOf(reader.getLocalName().toUpperCase());
                            break;
                        case ATTRACTIONS:
                            attraction = new Attraction();
                            attraction.setId(Integer.parseInt(reader.getAttributeValue(null, "id")));
                            break;
                        case ATTRACTION:
                            attractionTag = AttractionTag.valueOf(reader.getLocalName().toUpperCase());
                            break;
                        case SERVICEBUILDINGS:
                            serviceBuilding = new ServiceBuilding();
                            serviceBuilding.setId(Integer.parseInt(reader.getAttributeValue(null, "id")));
                            break;
                        case SERVICEBUILDING:
                            serviceBuildingTag = ServiceBuildingTag.valueOf(reader.getLocalName().toUpperCase());
                            break;
                    }
                    break;
                case XMLStreamConstants.CHARACTERS:
                    String text = reader.getText().trim();
                    if (text.isEmpty()) {
                        break;
                    }

                    switch (entityTag) {
                        case TERRITORY: {
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
                            }
                        }
                        break;
                        case ATTRACTION: {
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
                            }
                        }
                        break;
                        case SERVICEBUILDING: {
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
                            }
                            break;
                        }
                    }
                    break;
                case XMLStreamConstants.END_ELEMENT:
                    try {
                        entityTag = EntityTag.valueOf(reader.getLocalName().toUpperCase());
                    } catch (EnumConstantNotPresentException | IllegalArgumentException e) {
                        continue;
                    }
                    switch (entityTag) {
                        case AMUSEMENTPARK:
                        break;
                        case TERRITORIES:
                            amusementPark.setTerritories(territories);
                            //entityTag = EntityTag.NONE;
                            break;
                        case TERRITORY:
                            territories.add(territory);
                            territory = null;
                            territoryTag = TerritoryTag.NONE;
                            break;
                        case ATTRACTIONS:
                            amusementPark.setAttractions(attractions);
                            //entityTag = EntityTag.NONE;
                            break;
                        case ATTRACTION:
                            attractions.add(attraction);
                            attraction = null;
                            attractionTag = AttractionTag.NONE;
                            break;
                        case SERVICEBUILDINGS:
                            amusementPark.setServiceBuildings(serviceBuildings);
                            //entityTag = EntityTag.NONE;
                            break;
                        case SERVICEBUILDING:
                            serviceBuildings.add(serviceBuilding);
                            serviceBuilding = null;
                            serviceBuildingTag = ServiceBuildingTag.NONE;
                            break;
                    }
            }
        }
        return amusementPark;
    }
}
