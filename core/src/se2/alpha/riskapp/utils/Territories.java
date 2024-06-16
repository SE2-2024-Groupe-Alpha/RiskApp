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
    private static final TerritoryNode Alaska = new TerritoryNode("Alaska", NORTH_AMERICA, 261.0, 1854.0);
    private static final TerritoryNode NorthwestTerritory = new TerritoryNode("Northwest Territory", NORTH_AMERICA, 540.0, 1838.0);
    private static final TerritoryNode Greenland = new TerritoryNode("Greenland", NORTH_AMERICA, 1115.0, 1938.0);
    private static final TerritoryNode Alberta = new TerritoryNode("Alberta", NORTH_AMERICA, 532.0, 1678.0);
    private static final TerritoryNode Ontario = new TerritoryNode("Ontario", NORTH_AMERICA, 711.0, 1625.0);
    private static final TerritoryNode Quebec = new TerritoryNode("Quebec", NORTH_AMERICA, 911.0, 1646.0);
    private static final TerritoryNode WesternUnitedStates = new TerritoryNode("Western United States", NORTH_AMERICA, 521.0, 1438.0);
    private static final TerritoryNode EasternUnitedStates = new TerritoryNode("Eastern United States", NORTH_AMERICA, 735.0, 1394.0);
    private static final TerritoryNode CentralAmerica = new TerritoryNode("Central America", NORTH_AMERICA, 548.0, 1178.0);
    private static final TerritoryNode Venezuela = new TerritoryNode("Venezuela", SOUTH_AMERICA, 758.0, 997.0);
    private static final TerritoryNode Peru = new TerritoryNode("Peru", SOUTH_AMERICA, 753.0, 747.0);
    private static final TerritoryNode Brazil = new TerritoryNode("Brazil", SOUTH_AMERICA, 1001.0, 786.0);
    private static final TerritoryNode Argentina = new TerritoryNode("Argentina", SOUTH_AMERICA, 808.0, 408.0);
    private static final TerritoryNode Iceland = new TerritoryNode("Iceland", EUROPE, 1369.0, 1749.0);
    private static final TerritoryNode Scandinavia = new TerritoryNode("Scandinavia", EUROPE, 1585.0, 1735.0);
    private static final TerritoryNode Russia = new TerritoryNode("Russia", EUROPE, 1854.0, 1601.0);
    private static final TerritoryNode GreatBritain = new TerritoryNode("Great Britain", EUROPE, 1346.0, 1486.0);
    private static final TerritoryNode NorthernEurope = new TerritoryNode("Northern Europe", EUROPE, 1604.0, 1457.0);
    private static final TerritoryNode WesternEurope = new TerritoryNode("Western Europe", EUROPE, 1364.0, 1215.0);
    private static final TerritoryNode Austria = new TerritoryNode("Austria", EUROPE, 1628.0, 1299.0);
    private static final TerritoryNode NorthAfrica = new TerritoryNode("North Africa", AFRICA, 1490.0001, 852.0);
    private static final TerritoryNode Egypt = new TerritoryNode("Egypt", AFRICA, 1714.0001, 973.0);
    private static final TerritoryNode EastAfrica = new TerritoryNode("East Africa", AFRICA, 1864.0001, 731.0);
    private static final TerritoryNode Congo = new TerritoryNode("Congo", AFRICA, 1732.0001, 600.0);
    private static final TerritoryNode SouthAfrica = new TerritoryNode("South Africa", AFRICA, 1756.0001, 354.99994);
    private static final TerritoryNode Madagascar = new TerritoryNode("Madagascar", AFRICA, 2019.0, 330.99994);
    private static final TerritoryNode Ural = new TerritoryNode("Ural", ASIA, 2182.0, 1649.0);
    private static final TerritoryNode Siberia = new TerritoryNode("Siberia", ASIA, 2372.0, 1764.0);
    private static final TerritoryNode Yakutsk = new TerritoryNode("Yakutsk", ASIA, 2564.0, 1877.0);
    private static final TerritoryNode Kamchatka = new TerritoryNode("Kamchatka", ASIA, 2769.0, 1833.0);
    private static final TerritoryNode Irkutsk = new TerritoryNode("Irkutsk", ASIA, 2553.0, 1638.0);
    private static final TerritoryNode Mongolia = new TerritoryNode("Mongolia", ASIA, 2575.0, 1449.0);
    private static final TerritoryNode Japan = new TerritoryNode("Japan", ASIA, 2899.0, 1423.0);
    private static final TerritoryNode Afghanistan = new TerritoryNode("Afghanistan", ASIA, 2145.0, 1367.0);
    private static final TerritoryNode China = new TerritoryNode("China", ASIA, 2474.0, 1241.0);
    private static final TerritoryNode India = new TerritoryNode("India", ASIA, 2308.0, 1076.0);
    private static final TerritoryNode Siam = new TerritoryNode("Siam", ASIA, 2556.0, 997.0);
    private static final TerritoryNode Indonesia = new TerritoryNode("Indonesia", OCEANIA, 2618.0, 663.0);
    private static final TerritoryNode NewGuinea = new TerritoryNode("New Guinea", OCEANIA, 2881.0, 715.0);
    private static final TerritoryNode WesternAustralia = new TerritoryNode("Western Australia", OCEANIA, 2768.0, 354.99994);
    private static final TerritoryNode EasternAustralia = new TerritoryNode("Eastern Australia", OCEANIA, 2989.0, 402.0);
    private static final TerritoryNode MiddleEast = new TerritoryNode("Middle East", ASIA, 1967.0, 1057.0);


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

        Irkutsk.addAdjTerritory(Arrays.asList(Siberia, Yakutsk, Kamchatka, Mongolia));

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

    public static TerritoryNode getTerritoryByName(String name){
        return territoryMap.get(name);
    }
}
