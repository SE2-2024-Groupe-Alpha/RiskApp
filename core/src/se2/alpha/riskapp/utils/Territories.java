package se2.alpha.riskapp.utils;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import java.util.*;

public class Territories {
    private static final String NORTH_AMERICA = "North America";
    private static final String SOUTH_AMERICA = "South America";
    private static final String EUROPE = "Europe";
    private static final String AFRICA = "Africa";
    private static final String ASIA = "Asia";
    private static final String OCEANIA = "Oceania";
    private static final TerritoryNode Alaska = new TerritoryNode("Alaska", NORTH_AMERICA);
    private static final TerritoryNode NorthwestTerritory = new TerritoryNode("Northwest Territory", NORTH_AMERICA);
    private static final TerritoryNode Greenland = new TerritoryNode("Greenland", NORTH_AMERICA);
    private static final TerritoryNode Alberta = new TerritoryNode("Alberta", NORTH_AMERICA);
    private static final TerritoryNode Ontario = new TerritoryNode("Ontario", NORTH_AMERICA);
    private static final TerritoryNode Quebec = new TerritoryNode("Quebec", NORTH_AMERICA);
    private static final TerritoryNode WesternUnitedStates = new TerritoryNode("Western United States", NORTH_AMERICA);
    private static final TerritoryNode EasternUnitedStates = new TerritoryNode("Eastern United States", NORTH_AMERICA);
    private static final TerritoryNode CentralAmerica = new TerritoryNode("Central America", NORTH_AMERICA);
    private static final TerritoryNode Venezuela = new TerritoryNode("Venezuela", SOUTH_AMERICA);
    private static final TerritoryNode Peru = new TerritoryNode("Peru", SOUTH_AMERICA);
    private static final TerritoryNode Brazil = new TerritoryNode("Brazil", SOUTH_AMERICA);
    private static final TerritoryNode Argentina = new TerritoryNode("Argentina", SOUTH_AMERICA);
    private static final TerritoryNode Iceland = new TerritoryNode("Iceland", EUROPE);
    private static final TerritoryNode Scandinavia = new TerritoryNode("Scandinavia", EUROPE);
    private static final TerritoryNode Russia = new TerritoryNode("Russia", EUROPE);
    private static final TerritoryNode GreatBritain = new TerritoryNode("Great Britain", EUROPE);
    private static final TerritoryNode NorthernEurope = new TerritoryNode("Northern Europe", EUROPE);
    private static final TerritoryNode WesternEurope = new TerritoryNode("Western Europe", EUROPE);
    private static final TerritoryNode Austria = new TerritoryNode("Austria", EUROPE);
    private static final TerritoryNode NorthAfrica = new TerritoryNode("North Africa", AFRICA);
    private static final TerritoryNode Egypt = new TerritoryNode("Egypt", AFRICA);
    private static final TerritoryNode EastAfrica = new TerritoryNode("East Africa", AFRICA);
    private static final TerritoryNode Congo = new TerritoryNode("Congo", AFRICA);
    private static final TerritoryNode SouthAfrica = new TerritoryNode("South Africa", AFRICA);
    private static final TerritoryNode Madagascar = new TerritoryNode("Madagascar", AFRICA);
    private static final TerritoryNode Ural = new TerritoryNode("Ural", ASIA);
    private static final TerritoryNode Siberia = new TerritoryNode("Siberia", ASIA);
    private static final TerritoryNode Yakutsk = new TerritoryNode("Yakutsk", ASIA);
    private static final TerritoryNode Kamchatka = new TerritoryNode("Kamchatka", ASIA);
    private static final TerritoryNode Irkutsk = new TerritoryNode("Irkutsk", ASIA);
    private static final TerritoryNode Mongolia = new TerritoryNode("Mongolia", ASIA);
    private static final TerritoryNode Japan = new TerritoryNode("Japan", ASIA);
    private static final TerritoryNode Afghanistan = new TerritoryNode("Afghanistan", ASIA);
    private static final TerritoryNode China = new TerritoryNode("China", ASIA);
    private static final TerritoryNode India = new TerritoryNode("India", ASIA);
    private static final TerritoryNode Siam = new TerritoryNode("Siam", ASIA);
    private static final TerritoryNode Indonesia = new TerritoryNode("Indonesia", OCEANIA);
    private static final TerritoryNode NewGuinea = new TerritoryNode("New Guinea", OCEANIA);
    private static final TerritoryNode WesternAustralia = new TerritoryNode("Western Australia", OCEANIA);
    private static final TerritoryNode EasternAustralia = new TerritoryNode("Eastern Australia", OCEANIA);
    private static final TerritoryNode MiddleEast = new TerritoryNode("Middle East", ASIA);

    public static final Map<String, TerritoryNode> colorsToTerritories = new HashMap<>();

    private static final HashMap<String, TerritoryNode> territoryMap = new HashMap<>();

    static {
        Alaska.addAdjTerritory(Arrays.asList(NorthwestTerritory, Alberta, Kamchatka));

        NorthwestTerritory.addAdjTerritory(Arrays.asList(Alaska, Alberta, Ontario, Greenland));

        Greenland.addAdjTerritory(Arrays.asList(NorthwestTerritory, Quebec, Ontario, Iceland));

        Alberta.addAdjTerritory(Arrays.asList(Alaska, NorthwestTerritory, Ontario, WesternUnitedStates));

        Ontario.addAdjTerritory(Arrays.asList(Alberta, NorthwestTerritory, Quebec, Greenland));

        Quebec.addAdjTerritory(Arrays.asList(Ontario, EasternUnitedStates, Greenland));

        WesternUnitedStates.addAdjTerritory(Arrays.asList(Alberta, Ontario, EasternUnitedStates, CentralAmerica));

        EasternUnitedStates.addAdjTerritory(Arrays.asList(Ontario, Quebec, WesternUnitedStates, CentralAmerica));

        CentralAmerica.addAdjTerritory(Arrays.asList(WesternUnitedStates, EasternUnitedStates, Venezuela));

        Venezuela.addAdjTerritory(Arrays.asList(CentralAmerica, Peru, Brazil));

        Peru.addAdjTerritory(Arrays.asList(Venezuela, Brazil, Argentina));

        Brazil.addAdjTerritory(Arrays.asList(Venezuela, Peru, Argentina, NorthAfrica));

        Argentina.addAdjTerritory(Arrays.asList(Brazil, Peru));

        Iceland.addAdjTerritory(Arrays.asList(Greenland, GreatBritain, Scandinavia));

        Scandinavia.addAdjTerritory(Arrays.asList(Iceland, GreatBritain, NorthernEurope, Russia));

        Russia.addAdjTerritory(Arrays.asList(Scandinavia, NorthernEurope, Austria, MiddleEast, Afghanistan, Ural));

        GreatBritain.addAdjTerritory(Arrays.asList(Iceland, Scandinavia, NorthernEurope, WesternEurope));

        NorthernEurope.addAdjTerritory(Arrays.asList(Scandinavia, GreatBritain, WesternEurope, Austria, Russia));

        WesternEurope.addAdjTerritory(Arrays.asList(GreatBritain, NorthernEurope, Austria, NorthAfrica));

        Austria.addAdjTerritory(Arrays.asList(WesternEurope, NorthernEurope, Russia, MiddleEast, NorthAfrica, Egypt));

        NorthAfrica.addAdjTerritory(Arrays.asList(WesternEurope, Austria, Brazil, Egypt, EastAfrica, Congo));

        Egypt.addAdjTerritory(Arrays.asList(NorthAfrica, Austria, MiddleEast, EastAfrica));

        EastAfrica.addAdjTerritory(Arrays.asList(NorthAfrica, Egypt, Congo, MiddleEast, SouthAfrica, Madagascar));

        Congo.addAdjTerritory(Arrays.asList(NorthAfrica, EastAfrica, SouthAfrica));

        SouthAfrica.addAdjTerritory(Arrays.asList(Congo, EastAfrica, Madagascar));

        Madagascar.addAdjTerritory(Arrays.asList(SouthAfrica, EastAfrica));

        Ural.addAdjTerritory(Arrays.asList(Russia, Afghanistan, China, Siberia));

        Siberia.addAdjTerritory(Arrays.asList(Ural, Yakutsk, Irkutsk, Mongolia, China));

        Yakutsk.addAdjTerritory(Arrays.asList(Siberia, Kamchatka, Irkutsk));

        Kamchatka.addAdjTerritory(Arrays.asList(Alaska, Yakutsk, Irkutsk, Mongolia, Japan));

        Mongolia.addAdjTerritory(Arrays.asList(Japan, Kamchatka, Irkutsk, Siberia, China));

        Japan.addAdjTerritory(Arrays.asList(Mongolia, Kamchatka));

        Afghanistan.addAdjTerritory(Arrays.asList(Ural, China, India, MiddleEast, Russia));

        China.addAdjTerritory(Arrays.asList(Mongolia, Siberia, Ural, Afghanistan, India, Siam));

        India.addAdjTerritory(Arrays.asList(MiddleEast, Afghanistan, China, Siam));

        Siam.addAdjTerritory(Arrays.asList(India, China, Indonesia));

        Indonesia.addAdjTerritory(Arrays.asList(Siam, NewGuinea, WesternAustralia));

        NewGuinea.addAdjTerritory(Arrays.asList(Indonesia, WesternAustralia, EasternAustralia));

        WesternAustralia.addAdjTerritory(Arrays.asList(Indonesia, NewGuinea, EasternAustralia));

        EasternAustralia.addAdjTerritory(Arrays.asList(Indonesia, NewGuinea, WesternAustralia));

        MiddleEast.addAdjTerritory(Arrays.asList(Austria, Russia, Afghanistan, India, Egypt, EastAfrica));

        territoryMap.put("Alaska", Alaska);
        territoryMap.put("Northwest Territory", NorthwestTerritory);
        territoryMap.put("Greenland", Greenland);
        territoryMap.put("Alberta", Alberta);
        territoryMap.put("Ontario", Ontario);
        territoryMap.put("Quebec", Quebec);
        territoryMap.put("Western United States", WesternUnitedStates);
        territoryMap.put("Eastern United States", EasternUnitedStates);
        territoryMap.put("Central America", CentralAmerica);
        territoryMap.put("Venezuela", Venezuela);
        territoryMap.put("Peru", Peru);
        territoryMap.put("Brazil", Brazil);
        territoryMap.put("Argentina", Argentina);
        territoryMap.put("Iceland", Iceland);
        territoryMap.put("Scandinavia", Scandinavia);
        territoryMap.put("Russia", Russia);
        territoryMap.put("Great Britain", GreatBritain);
        territoryMap.put("Northern Europe", NorthernEurope);
        territoryMap.put("Western Europe", WesternEurope);
        territoryMap.put("Austria", Austria);
        territoryMap.put("North Africa", NorthAfrica);
        territoryMap.put("Egypt", Egypt);
        territoryMap.put("East Africa", EastAfrica);
        territoryMap.put("Congo", Congo);
        territoryMap.put("South Africa", SouthAfrica);
        territoryMap.put("Madagascar", Madagascar);
        territoryMap.put("Ural", Ural);
        territoryMap.put("Siberia", Siberia);
        territoryMap.put("Yakutsk", Yakutsk);
        territoryMap.put("Kamchatka", Kamchatka);
        territoryMap.put("Irkutsk", Irkutsk);
        territoryMap.put("Mongolia", Mongolia);
        territoryMap.put("Japan", Japan);
        territoryMap.put("Afghanistan", Afghanistan);
        territoryMap.put("China", China);
        territoryMap.put("India", India);
        territoryMap.put("Siam", Siam);
        territoryMap.put("Indonesia", Indonesia);
        territoryMap.put("New Guinea", NewGuinea);
        territoryMap.put("Western Australia", WesternAustralia);
        territoryMap.put("Eastern Australia", EasternAustralia);
        territoryMap.put("Middle East", MiddleEast);

        colorsToTerritories.put("FF6347", Alaska);
        colorsToTerritories.put("4682B4", NorthwestTerritory);
        colorsToTerritories.put("D8BFD8", Greenland);
        colorsToTerritories.put("FFD700", Alberta);
        colorsToTerritories.put("D2691E", Ontario);
        colorsToTerritories.put("8A2BE2", Quebec);
        colorsToTerritories.put("DC143C", WesternUnitedStates);
        colorsToTerritories.put("00FFFF", EasternUnitedStates);
        colorsToTerritories.put("00008B", CentralAmerica);
        colorsToTerritories.put("008000", Venezuela);
        colorsToTerritories.put("FF4500", Peru);
        colorsToTerritories.put("6A5ACD", Brazil);
        colorsToTerritories.put("8B4513", Argentina);
        colorsToTerritories.put("FA8072", Iceland);
        colorsToTerritories.put("EEE8AA", Scandinavia);
        colorsToTerritories.put("A0522D", Russia);
        colorsToTerritories.put("2E8B57", GreatBritain);
        colorsToTerritories.put("FF1493", NorthernEurope);
        colorsToTerritories.put("483D8B", WesternEurope);
        colorsToTerritories.put("B8860B", Austria);
        colorsToTerritories.put("5F9EA0", NorthAfrica);
        colorsToTerritories.put("9ACD32", Egypt);
        colorsToTerritories.put("8B0000", EastAfrica);
        colorsToTerritories.put("FFA500", Congo);
        colorsToTerritories.put("006400", SouthAfrica);
        colorsToTerritories.put("800080", Madagascar);
        colorsToTerritories.put("FF00FF", Ural);
        colorsToTerritories.put("A52A2A", Siberia);
        colorsToTerritories.put("DEB887", Yakutsk);
        colorsToTerritories.put("85F8FF", Kamchatka);
        colorsToTerritories.put("7FFF00", Irkutsk);
        colorsToTerritories.put("FFDEAD", Mongolia);
        colorsToTerritories.put("FF0000", Japan);
        colorsToTerritories.put("0000CD", Afghanistan);
        colorsToTerritories.put("BA55D3", China);
        colorsToTerritories.put("4B0082", India);
        colorsToTerritories.put("F08080", Siam);
        colorsToTerritories.put("20B2AA", Indonesia);
        colorsToTerritories.put("FFFAF0", NewGuinea);
        colorsToTerritories.put("228B22", WesternAustralia);
        colorsToTerritories.put("ADFF2F", EasternAustralia);
        colorsToTerritories.put("F0E68C", MiddleEast);
    }

    public static Map<String, TerritoryNode> getAllTerritories()
    {
        return territoryMap;
    }

    public static TerritoryNode getTerritoryByColor(String colorKey) {
        return colorsToTerritories.get(colorKey);
    }

    public static TerritoryNode getTerritoryByColor(Color color) {

        String colorKey = color.toString().substring(0, 6).toUpperCase();
        TerritoryNode territoryNode = Territories.getTerritoryByColor(colorKey);

        if (territoryNode == null) {
            System.out.println("Territory node not found for color: " + colorKey);
            return null;
        }

        System.out.println(territoryNode.getAdjTerritories());
        return territoryNode;
    }
}
