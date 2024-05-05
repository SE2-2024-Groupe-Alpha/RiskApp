package se2.alpha.riskapp.utils;

import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Territories {
    private static final TerritoryNode Alaska = new TerritoryNode("Alaska");
    private static final TerritoryNode NorthwestTerritory = new TerritoryNode("Northwest Territory");
    private static final TerritoryNode Greenland = new TerritoryNode("Greenland");
    private static final TerritoryNode Alberta = new TerritoryNode("Alberta");
    private static final TerritoryNode Ontario = new TerritoryNode("Ontario");
    private static final TerritoryNode Quebec = new TerritoryNode("Quebec");
    private static final TerritoryNode WesternUnitedStates = new TerritoryNode("Western United States");
    private static final TerritoryNode EasternUnitedStates = new TerritoryNode("Eastern United States");
    private static final TerritoryNode CentralAmerica = new TerritoryNode("Central America");
    private static final TerritoryNode Venezuela = new TerritoryNode("Venezuela");
    private static final TerritoryNode Peru = new TerritoryNode("Peru");
    private static final TerritoryNode Brazil = new TerritoryNode("Brazil");
    private static final TerritoryNode Argentina = new TerritoryNode("Argentina");
    private static final TerritoryNode Iceland = new TerritoryNode("Iceland");
    private static final TerritoryNode Scandinavia = new TerritoryNode("Scandinavia");
    private static final TerritoryNode Russia = new TerritoryNode("Russia");
    private static final TerritoryNode GreatBritain = new TerritoryNode("Great Britain");
    private static final TerritoryNode NorthernEurope = new TerritoryNode("Northern Europe");
    private static final TerritoryNode WesternEurope = new TerritoryNode("Western Europe");
    private static final TerritoryNode Austria = new TerritoryNode("Austria");
    private static final TerritoryNode NorthAfrica = new TerritoryNode("North Africa");
    private static final TerritoryNode Egypt = new TerritoryNode("Egypt");
    private static final TerritoryNode EastAfrica = new TerritoryNode("East Africa");
    private static final TerritoryNode Congo = new TerritoryNode("Congo");
    private static final TerritoryNode SouthAfrica = new TerritoryNode("South Africa");
    private static final TerritoryNode Madagascar = new TerritoryNode("Madagascar");
    private static final TerritoryNode Ural = new TerritoryNode("Ural");
    private static final TerritoryNode Siberia = new TerritoryNode("Siberia");
    private static final TerritoryNode Yakutsk = new TerritoryNode("Yakutsk");
    private static final TerritoryNode Kamchatka = new TerritoryNode("Kamchatka");
    private static final TerritoryNode Irkutsk = new TerritoryNode("Irkutsk");
    private static final TerritoryNode Mongolia = new TerritoryNode("Mongolia");
    private static final TerritoryNode Japan = new TerritoryNode("Japan");
    private static final TerritoryNode Afghanistan = new TerritoryNode("Afghanistan");
    private static final TerritoryNode China = new TerritoryNode("China");
    private static final TerritoryNode India = new TerritoryNode("India");
    private static final TerritoryNode Siam = new TerritoryNode("Siam");
    private static final TerritoryNode Indonesia = new TerritoryNode("Indonesia");
    private static final TerritoryNode NewGuinea = new TerritoryNode("New Guinea");
    private static final TerritoryNode WesternAustralia = new TerritoryNode("Western Australia");
    private static final TerritoryNode EasternAustralia = new TerritoryNode("Eastern Australia");
    private static final TerritoryNode MiddleEast = new TerritoryNode("Middle East");

    private static final Map<String, TerritoryNode> colorsToTerritories = new HashMap<>();

    static {
        Alaska.addAdjTerritory(
            new ArrayList<TerritoryNode>() {{
                add(NorthwestTerritory);
                add(Alberta);
                add(Kamchatka);
            }}
        );

        NorthwestTerritory.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Alaska);
                    add(Alberta);
                    add(Ontario);
                    add(Greenland);
                }}
        );

        Greenland.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(NorthwestTerritory);
                    add(Quebec);
                    add(Ontario);
                    add(Iceland);
                }}
        );

        Alberta.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Alaska);
                    add(NorthwestTerritory);
                    add(Ontario);
                    add(WesternUnitedStates);
                }}
        );

        Ontario.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Alberta);
                    add(NorthwestTerritory);
                    add(Quebec);
                    add(Greenland);
                }}
        );

        Quebec.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Ontario);
                    add(EasternUnitedStates);
                    add(Greenland);
                }}
        );

        WesternUnitedStates.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Alberta);
                    add(Ontario);
                    add(EasternUnitedStates);
                    add(CentralAmerica);
                }}
        );

        EasternUnitedStates.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Ontario);
                    add(Quebec);
                    add(WesternUnitedStates);
                    add(CentralAmerica);
                }}
        );


        CentralAmerica.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(WesternUnitedStates);
                    add(EasternUnitedStates);
                    add(Venezuela);
                }}
        );

        Venezuela.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(CentralAmerica);
                    add(Peru);
                    add(Brazil);
                }}
        );

        Peru.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Venezuela);
                    add(Brazil);
                    add(Argentina);
                }}
        );

        Brazil.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Venezuela);
                    add(Peru);
                    add(Argentina);
                    add(NorthAfrica);
                }}
        );

        Argentina.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Brazil);
                    add(Peru);
                }}
        );

        Iceland.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Greenland);
                    add(GreatBritain);
                    add(Scandinavia);
                }}
        );

        Scandinavia.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Iceland);
                    add(GreatBritain);
                    add(NorthernEurope);
                    add(Russia);
                }}
        );

        Russia.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Scandinavia);
                    add(NorthernEurope);
                    add(Austria);
                    add(MiddleEast);
                    add(Afghanistan);
                    add(Ural);
                }}
        );

        GreatBritain.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Iceland);
                    add(Scandinavia);
                    add(NorthernEurope);
                    add(WesternEurope);
                }}
        );

        NorthernEurope.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Scandinavia);
                    add(GreatBritain);
                    add(WesternEurope);
                    add(Austria);
                    add(Russia);
                }}
        );

        WesternEurope.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(GreatBritain);
                    add(NorthernEurope);
                    add(Austria);
                    add(NorthAfrica);
                }}
        );

        Austria.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(WesternEurope);
                    add(NorthernEurope);
                    add(Russia);
                    add(MiddleEast);
                    add(NorthAfrica);
                    add(Egypt);
                }}
        );

        NorthAfrica.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(WesternEurope);
                    add(Austria);
                    add(Brazil);
                    add(Egypt);
                    add(EastAfrica);
                    add(Congo);
                }}
        );

        Egypt.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(NorthAfrica);
                    add(Austria);
                    add(MiddleEast);
                    add(EastAfrica);
                }}
        );

        EastAfrica.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(NorthAfrica);
                    add(Egypt);
                    add(Congo);
                    add(MiddleEast);
                    add(SouthAfrica);
                    add(Madagascar);
                }}
        );

        Congo.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(NorthAfrica);
                    add(EastAfrica);
                    add(SouthAfrica);
                }}
        );

        SouthAfrica.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Congo);
                    add(EastAfrica);
                    add(Madagascar);
                }}
        );

        Madagascar.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(SouthAfrica);
                    add(EastAfrica);
                }}
        );

        Ural.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Russia);
                    add(Afghanistan);
                    add(China);
                    add(Siberia);
                }}
        );

        Siberia.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Ural);
                    add(Yakutsk);
                    add(Irkutsk);
                    add(Mongolia);
                    add(China);
                }}
        );

        Yakutsk.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Siberia);
                    add(Kamchatka);
                    add(Irkutsk);
                }}
        );

        Kamchatka.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Alaska);
                    add(Yakutsk);
                    add(Irkutsk);
                    add(Mongolia);
                    add(Japan);
                }}
        );

        Mongolia.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Japan);
                    add(Kamchatka);
                    add(Irkutsk);
                    add(Siberia);
                    add(China);
                }}
        );

        Japan.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Mongolia);
                    add(Kamchatka);
                }}
        );

        Afghanistan.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Ural);
                    add(China);
                    add(India);
                    add(MiddleEast);
                    add(Russia);
                }}
        );

        China.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Mongolia);
                    add(Siberia);
                    add(Ural);
                    add(Afghanistan);
                    add(India);
                    add(Siam);
                }}
        );

        India.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(MiddleEast);
                    add(Afghanistan);
                    add(China);
                    add(Siam);
                }}
        );

        Siam.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(India);
                    add(China);
                    add(Indonesia);
                }}
        );

        Indonesia.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Siam);
                    add(NewGuinea);
                    add(WesternAustralia);
                }}
        );

        NewGuinea.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Indonesia);
                    add(WesternAustralia);
                    add(EasternAustralia);
                }}
        );

        WesternAustralia.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Indonesia);
                    add(NewGuinea);
                    add(EasternAustralia);
                }}
        );

        EasternAustralia.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Indonesia);
                    add(NewGuinea);
                    add(WesternAustralia);
                }}
        );

        MiddleEast.addAdjTerritory(
                new ArrayList<TerritoryNode>() {{
                    add(Austria);
                    add(Russia);
                    add(Afghanistan);
                    add(India);
                    add(Egypt);
                    add(EastAfrica);
                }}
        );

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


    public static TerritoryNode getTerritoryByColor(String colorKey) {
        return colorsToTerritories.get(colorKey);
    }

}
