/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbd4topostgres.model;

/**
 *
 * @author frank
 */
public class DBDesignerModel4 {
    // INDEX_TYPE

    public static String INDEX_TYPE_PRIMARY = "0";
    public static String INDEX_TYPE_INDEX = "1";
    public static String INDEX_TYPE_UNIQUE_INDEX = "2";
    public static String INDEX_TYPE_FULL_TEXT_INDEX = "3";
    // BOLLEAN
    public static String FALSE = "0";
    public static String TRUE = "1";
    // RELATIONS - ON DELETE/ON UPDATE
    public static String RELATION_ON_UPDATE_DELETE_RESTRICT = "0";
    public static String RELATION_ON_UPDATE_DELETE_CASCADE = "1";
    public static String RELATION_ON_UPDATE_DELETE_SET_NULL = "2";
    public static String RELATION_ON_UPDATE_DELETE_NO_ACTION = "3";
    public static String RELATION_ON_UPDATE_DELETE_SET_DEFAULT = "4";
    // RELATION - REFERENCE DEFINITION
    public static String RELATION_REFERENCE_DEFINITION = "0";
    public static String RELATION_OPTIONAL_RELATION = "1";
    public static String RELATION_COMMENTS = "2";

    public static String getDescriptionOnDeleteUpdate(String idRestriction) {
        String description = "";
        if (idRestriction.equals(DBDesignerModel4.RELATION_ON_UPDATE_DELETE_RESTRICT)) {
            description = "RESTRICT";
        } else if (idRestriction.equals(DBDesignerModel4.RELATION_ON_UPDATE_DELETE_CASCADE)) {
            description = "CASCADE";
        } else if (idRestriction.equals(DBDesignerModel4.RELATION_ON_UPDATE_DELETE_SET_NULL)) {
            description = "SET NULL";
        } else if (idRestriction.equals(DBDesignerModel4.RELATION_ON_UPDATE_DELETE_NO_ACTION)) {
            description = "NO ACTION";
        } else if (idRestriction.equals(DBDesignerModel4.RELATION_ON_UPDATE_DELETE_SET_DEFAULT)) {
            description = "SET DEFAULT";
        }
        return description;
    }
    
    
    public static String normalizeAttribute(String stringOriginal) {
        String stringConvertida = stringOriginal;
        stringConvertida = stringConvertida.replaceAll("\\\\n", "\r\n");
        stringConvertida = stringConvertida.replaceAll("\\\\a", "'");
        stringConvertida = stringConvertida.replaceAll("\\\\A", "\"");
        stringConvertida = stringConvertida.replaceAll("\\\\192", "À");
        stringConvertida = stringConvertida.replaceAll("\\\\193", "Á");
        stringConvertida = stringConvertida.replaceAll("\\\\194", "Â");
        stringConvertida = stringConvertida.replaceAll("\\\\195", "Ã");
        stringConvertida = stringConvertida.replaceAll("\\\\196", "Ä");
        stringConvertida = stringConvertida.replaceAll("\\\\197", "Å");
        stringConvertida = stringConvertida.replaceAll("\\\\198", "Æ");
        stringConvertida = stringConvertida.replaceAll("\\\\199", "Ç");
        stringConvertida = stringConvertida.replaceAll("\\\\200", "È");
        stringConvertida = stringConvertida.replaceAll("\\\\201", "É");
        stringConvertida = stringConvertida.replaceAll("\\\\202", "Ê");
        stringConvertida = stringConvertida.replaceAll("\\\\203", "Ë");
        stringConvertida = stringConvertida.replaceAll("\\\\204", "Ì");
        stringConvertida = stringConvertida.replaceAll("\\\\205", "Í");
        stringConvertida = stringConvertida.replaceAll("\\\\206", "Î");
        stringConvertida = stringConvertida.replaceAll("\\\\207", "Ï");
        stringConvertida = stringConvertida.replaceAll("\\\\208", "Ð");
        stringConvertida = stringConvertida.replaceAll("\\\\209", "Ñ");
        stringConvertida = stringConvertida.replaceAll("\\\\210", "Ò");
        stringConvertida = stringConvertida.replaceAll("\\\\211", "Ó");
        stringConvertida = stringConvertida.replaceAll("\\\\212", "Ô");
        stringConvertida = stringConvertida.replaceAll("\\\\213", "Õ");
        stringConvertida = stringConvertida.replaceAll("\\\\214", "Ö");
        stringConvertida = stringConvertida.replaceAll("\\\\215", "×");
        stringConvertida = stringConvertida.replaceAll("\\\\216", "Ø");
        stringConvertida = stringConvertida.replaceAll("\\\\217", "Ù");
        stringConvertida = stringConvertida.replaceAll("\\\\218", "Ú");
        stringConvertida = stringConvertida.replaceAll("\\\\219", "Û");
        stringConvertida = stringConvertida.replaceAll("\\\\220", "Ü");
        stringConvertida = stringConvertida.replaceAll("\\\\221", "Ý");
        stringConvertida = stringConvertida.replaceAll("\\\\222", "Þ");
        stringConvertida = stringConvertida.replaceAll("\\\\223", "ß");
        stringConvertida = stringConvertida.replaceAll("\\\\224", "à");
        stringConvertida = stringConvertida.replaceAll("\\\\225", "á");
        stringConvertida = stringConvertida.replaceAll("\\\\226", "â");
        stringConvertida = stringConvertida.replaceAll("\\\\227", "ã");
        stringConvertida = stringConvertida.replaceAll("\\\\228", "ä");
        stringConvertida = stringConvertida.replaceAll("\\\\229", "å");
        stringConvertida = stringConvertida.replaceAll("\\\\230", "æ");
        stringConvertida = stringConvertida.replaceAll("\\\\231", "ç");
        stringConvertida = stringConvertida.replaceAll("\\\\232", "è");
        stringConvertida = stringConvertida.replaceAll("\\\\233", "é");
        stringConvertida = stringConvertida.replaceAll("\\\\234", "ê");
        stringConvertida = stringConvertida.replaceAll("\\\\235", "ë");
        stringConvertida = stringConvertida.replaceAll("\\\\236", "ì");
        stringConvertida = stringConvertida.replaceAll("\\\\237", "í");
        stringConvertida = stringConvertida.replaceAll("\\\\238", "î");
        stringConvertida = stringConvertida.replaceAll("\\\\239", "ï");
        stringConvertida = stringConvertida.replaceAll("\\\\240", "ð");
        stringConvertida = stringConvertida.replaceAll("\\\\241", "ñ");
        stringConvertida = stringConvertida.replaceAll("\\\\242", "ò");
        stringConvertida = stringConvertida.replaceAll("\\\\243", "ó");
        stringConvertida = stringConvertida.replaceAll("\\\\244", "ô");
        stringConvertida = stringConvertida.replaceAll("\\\\245", "õ");
        stringConvertida = stringConvertida.replaceAll("\\\\246", "ö");
        stringConvertida = stringConvertida.replaceAll("\\\\247", "÷");
        stringConvertida = stringConvertida.replaceAll("\\\\248", "ø");
        stringConvertida = stringConvertida.replaceAll("\\\\249", "ù");
        stringConvertida = stringConvertida.replaceAll("\\\\250", "ú");
        stringConvertida = stringConvertida.replaceAll("\\\\251", "û");
        stringConvertida = stringConvertida.replaceAll("\\\\252", "ü");
        stringConvertida = stringConvertida.replaceAll("\\\\253", "ý");
        stringConvertida = stringConvertida.replaceAll("\\\\254", "þ");
        stringConvertida = stringConvertida.replaceAll("\\\\255", "ÿ");
        //
        return stringConvertida;
    }
}
