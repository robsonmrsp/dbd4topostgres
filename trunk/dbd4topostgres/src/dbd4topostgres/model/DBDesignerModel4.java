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

    public static final String INDEX_TYPE_PRIMARY = "0";
    public static final String INDEX_TYPE_INDEX = "1";
    public static final String INDEX_TYPE_UNIQUE_INDEX = "2";
    public static final String INDEX_TYPE_FULL_TEXT_INDEX = "3";
    // BOLLEAN
    public static final String FALSE = "0";
    public static final String TRUE = "1";
    // RELATIONS - ON DELETE/ON UPDATE
    public static final String RELATION_ON_UPDATE_DELETE_RESTRICT = "0";
    public static final String RELATION_ON_UPDATE_DELETE_CASCADE = "1";
    public static final String RELATION_ON_UPDATE_DELETE_SET_NULL = "2";
    public static final String RELATION_ON_UPDATE_DELETE_NO_ACTION = "3";
    public static final String RELATION_ON_UPDATE_DELETE_SET_DEFAULT = "4";
    // RELATION - REFERENCE DEFINITION
    public static final String RELATION_REFERENCE_DEFINITION = "0";
    public static final String RELATION_OPTIONAL_RELATION = "1";
    public static final String RELATION_COMMENTS = "2";

     public static String operationSystem = "Windows";

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

    public static String normalizeAttribute(String sourceString) {
        
        //
        if (operationSystem.equals("Windows")){
            return getStringCP1252Windows(sourceString);
        }
        // else if is linux
        return getStringUTF8Linux(sourceString);
    }

    public static String getStringUTF8Linux(String sourceString) {
        // In linux, get UTF-8(dec.)
        String resultString = sourceString;
        resultString = resultString.replaceAll("\\\\n", "\r\n");
        resultString = resultString.replaceAll("\\\\a", "'");
        resultString = resultString.replaceAll("\\\\A", "\"");
        resultString = resultString.replaceAll("\\\\k", "<");
        resultString = resultString.replaceAll("\\\\g", ">");
        //
        resultString = resultString.replaceAll("\\\\126", "~");
        resultString = resultString.replaceAll("\\\\194\\\\127", "");
        resultString = resultString.replaceAll("\\\\194\\\\128", "€");
        resultString = resultString.replaceAll("\\\\194\\\\129", "�");
        resultString = resultString.replaceAll("\\\\194\\\\130", "‚");
        resultString = resultString.replaceAll("\\\\194\\\\131", "ƒ");
        resultString = resultString.replaceAll("\\\\194\\\\132", "„");
        resultString = resultString.replaceAll("\\\\194\\\\133", "…");
        resultString = resultString.replaceAll("\\\\194\\\\134", "†");
        resultString = resultString.replaceAll("\\\\194\\\\135", "‡");
        resultString = resultString.replaceAll("\\\\194\\\\136", "ˆ");
        resultString = resultString.replaceAll("\\\\194\\\\137", "‰");
        resultString = resultString.replaceAll("\\\\194\\\\138", "Š");
        resultString = resultString.replaceAll("\\\\194\\\\139", "‹");
        resultString = resultString.replaceAll("\\\\194\\\\140", "Œ");
        resultString = resultString.replaceAll("\\\\194\\\\141", "�");
        resultString = resultString.replaceAll("\\\\194\\\\142", "Ž");
        resultString = resultString.replaceAll("\\\\194\\\\143", "�");
        resultString = resultString.replaceAll("\\\\194\\\\144", "�");
        resultString = resultString.replaceAll("\\\\194\\\\145", "‘");
        resultString = resultString.replaceAll("\\\\194\\\\146", "’");
        resultString = resultString.replaceAll("\\\\194\\\\147", "“");
        resultString = resultString.replaceAll("\\\\194\\\\148", "”");
        resultString = resultString.replaceAll("\\\\194\\\\149", "•");
        resultString = resultString.replaceAll("\\\\194\\\\150", "–");
        resultString = resultString.replaceAll("\\\\194\\\\151", "—");
        resultString = resultString.replaceAll("\\\\194\\\\152", "˜");
        resultString = resultString.replaceAll("\\\\194\\\\153", "™");
        resultString = resultString.replaceAll("\\\\194\\\\154", "š");
        resultString = resultString.replaceAll("\\\\194\\\\155", "›");
        resultString = resultString.replaceAll("\\\\194\\\\156", "œ");
        resultString = resultString.replaceAll("\\\\194\\\\157", "�");
        resultString = resultString.replaceAll("\\\\194\\\\158", "ž");
        resultString = resultString.replaceAll("\\\\194\\\\159", "Ÿ");
        resultString = resultString.replaceAll("\\\\194\\\\160", "¿");
        
        //resultString = resultString.replaceAll("\\\\194\\\\160", "");

        resultString = resultString.replaceAll("\\\\194\\\\161", "¡");
        resultString = resultString.replaceAll("\\\\194\\\\162", "¢");
        resultString = resultString.replaceAll("\\\\194\\\\163", "£");
        resultString = resultString.replaceAll("\\\\194\\\\164", "¤");
        resultString = resultString.replaceAll("\\\\194\\\\165", "¥");
        resultString = resultString.replaceAll("\\\\194\\\\166", "¦");
        resultString = resultString.replaceAll("\\\\194\\\\167", "§");
        resultString = resultString.replaceAll("\\\\194\\\\168", "¨");
        resultString = resultString.replaceAll("\\\\194\\\\169", "©");
        resultString = resultString.replaceAll("\\\\194\\\\170", "ª");
        resultString = resultString.replaceAll("\\\\194\\\\171", "«");
        resultString = resultString.replaceAll("\\\\194\\\\172", "¬");
        resultString = resultString.replaceAll("\\\\194\\\\173", "-");
        resultString = resultString.replaceAll("\\\\194\\\\174", "®");
        resultString = resultString.replaceAll("\\\\194\\\\175", "¯");
        resultString = resultString.replaceAll("\\\\194\\\\176", "°");
        resultString = resultString.replaceAll("\\\\194\\\\177", "±");
        resultString = resultString.replaceAll("\\\\194\\\\178", "²");
        resultString = resultString.replaceAll("\\\\194\\\\179", "³");
        resultString = resultString.replaceAll("\\\\194\\\\180", "´");
        resultString = resultString.replaceAll("\\\\194\\\\181", "µ");
        resultString = resultString.replaceAll("\\\\194\\\\182", "¶");
        resultString = resultString.replaceAll("\\\\194\\\\183", "·");
        resultString = resultString.replaceAll("\\\\194\\\\184", "¸");
        resultString = resultString.replaceAll("\\\\194\\\\185", "¹");
        resultString = resultString.replaceAll("\\\\194\\\\186", "º");
        resultString = resultString.replaceAll("\\\\194\\\\187", "»");
        resultString = resultString.replaceAll("\\\\194\\\\188", "¼");
        resultString = resultString.replaceAll("\\\\194\\\\189", "½");
        resultString = resultString.replaceAll("\\\\194\\\\190", "¾");
        resultString = resultString.replaceAll("\\\\194\\\\191", "¿");
        //

        resultString = resultString.replaceAll("\\\\195\\\\128", "À");
        resultString = resultString.replaceAll("\\\\195\\\\129", "Á");
        resultString = resultString.replaceAll("\\\\195\\\\130", "Â");
        resultString = resultString.replaceAll("\\\\195\\\\131", "Ã");
        resultString = resultString.replaceAll("\\\\195\\\\132", "Ä");
        resultString = resultString.replaceAll("\\\\195\\\\133", "Å");
        resultString = resultString.replaceAll("\\\\195\\\\134", "Æ");
        resultString = resultString.replaceAll("\\\\195\\\\135", "Ç");
        resultString = resultString.replaceAll("\\\\195\\\\136", "È");
        resultString = resultString.replaceAll("\\\\195\\\\137", "É");
        resultString = resultString.replaceAll("\\\\195\\\\138", "Ê");
        resultString = resultString.replaceAll("\\\\195\\\\139", "Ë");
        resultString = resultString.replaceAll("\\\\195\\\\140", "Ì");
        resultString = resultString.replaceAll("\\\\195\\\\141", "Í");
        resultString = resultString.replaceAll("\\\\195\\\\142", "Î");
        resultString = resultString.replaceAll("\\\\195\\\\143", "Ï");
        resultString = resultString.replaceAll("\\\\195\\\\144", "Ð");
        resultString = resultString.replaceAll("\\\\195\\\\145", "Ñ");
        resultString = resultString.replaceAll("\\\\195\\\\146", "Ò");
        resultString = resultString.replaceAll("\\\\195\\\\147", "Ó");
        resultString = resultString.replaceAll("\\\\195\\\\148", "Ô");
        resultString = resultString.replaceAll("\\\\195\\\\149", "Õ");
        resultString = resultString.replaceAll("\\\\195\\\\150", "Ö");
        resultString = resultString.replaceAll("\\\\195\\\\151", "×");
        resultString = resultString.replaceAll("\\\\195\\\\152", "Ø");
        resultString = resultString.replaceAll("\\\\195\\\\153", "Ù");
        resultString = resultString.replaceAll("\\\\195\\\\154", "Ú");
        resultString = resultString.replaceAll("\\\\195\\\\155", "Û");
        resultString = resultString.replaceAll("\\\\195\\\\156", "Ü");
        resultString = resultString.replaceAll("\\\\195\\\\157", "Ý");
        resultString = resultString.replaceAll("\\\\195\\\\158", "Þ");
        resultString = resultString.replaceAll("\\\\195\\\\159", "ß");
        resultString = resultString.replaceAll("\\\\195\\\\160", "à");
        resultString = resultString.replaceAll("\\\\195\\\\161", "á");
        resultString = resultString.replaceAll("\\\\195\\\\162", "â");
        resultString = resultString.replaceAll("\\\\195\\\\163", "ã");
        resultString = resultString.replaceAll("\\\\195\\\\164", "ä");
        resultString = resultString.replaceAll("\\\\195\\\\165", "å");
        resultString = resultString.replaceAll("\\\\195\\\\166", "æ");
        resultString = resultString.replaceAll("\\\\195\\\\167", "ç");
        resultString = resultString.replaceAll("\\\\195\\\\168", "è");
        resultString = resultString.replaceAll("\\\\195\\\\169", "é");
        resultString = resultString.replaceAll("\\\\195\\\\170", "ê");
        resultString = resultString.replaceAll("\\\\195\\\\171", "ë");
        resultString = resultString.replaceAll("\\\\195\\\\172", "ì");
        resultString = resultString.replaceAll("\\\\195\\\\173", "í");
        resultString = resultString.replaceAll("\\\\195\\\\174", "î");
        resultString = resultString.replaceAll("\\\\195\\\\175", "ï");
        resultString = resultString.replaceAll("\\\\195\\\\176", "ð");
        resultString = resultString.replaceAll("\\\\195\\\\177", "ñ");
        resultString = resultString.replaceAll("\\\\195\\\\178", "ò");
        resultString = resultString.replaceAll("\\\\195\\\\179", "ó");
        resultString = resultString.replaceAll("\\\\195\\\\180", "ô");
        resultString = resultString.replaceAll("\\\\195\\\\181", "õ");
        resultString = resultString.replaceAll("\\\\195\\\\182", "ö");
        resultString = resultString.replaceAll("\\\\195\\\\183", "÷");
        resultString = resultString.replaceAll("\\\\195\\\\184", "ø");
        resultString = resultString.replaceAll("\\\\195\\\\185", "ù");
        resultString = resultString.replaceAll("\\\\195\\\\186", "ú");
        resultString = resultString.replaceAll("\\\\195\\\\187", "û");
        resultString = resultString.replaceAll("\\\\195\\\\188", "ü");
        resultString = resultString.replaceAll("\\\\195\\\\189", "ý");
        resultString = resultString.replaceAll("\\\\195\\\\190", "þ");
        resultString = resultString.replaceAll("\\\\195\\\\191", "ÿ");
        //
        //

        resultString = resultString.replaceAll("\\\\\\+", "&");
        //
        return resultString;
    }

     public static String getStringCP1252Windows(String sourceString) {
        // In Windows get Code Page 1252
        String resultString = sourceString;
        resultString = resultString.replaceAll("\\\\n", "\r\n");
        resultString = resultString.replaceAll("\\\\a", "'");
        resultString = resultString.replaceAll("\\\\A", "\"");
        resultString = resultString.replaceAll("\\\\k", "<");
        resultString = resultString.replaceAll("\\\\g", ">");
        //
        resultString = resultString.replaceAll("\\\\126", "~");
        resultString = resultString.replaceAll("\\\\127", "");
        resultString = resultString.replaceAll("\\\\128", "€");
        resultString = resultString.replaceAll("\\\\129", "�");
        resultString = resultString.replaceAll("\\\\130", "‚");
        resultString = resultString.replaceAll("\\\\131", "ƒ");
        resultString = resultString.replaceAll("\\\\132", "„");
        resultString = resultString.replaceAll("\\\\133", "…");
        resultString = resultString.replaceAll("\\\\134", "†");
        resultString = resultString.replaceAll("\\\\135", "‡");
        resultString = resultString.replaceAll("\\\\136", "ˆ");
        resultString = resultString.replaceAll("\\\\137", "‰");
        resultString = resultString.replaceAll("\\\\138", "Š");
        resultString = resultString.replaceAll("\\\\139", "‹");
        resultString = resultString.replaceAll("\\\\140", "Œ");
        resultString = resultString.replaceAll("\\\\141", "�");
        resultString = resultString.replaceAll("\\\\142", "Ž");
        resultString = resultString.replaceAll("\\\\143", "�");
        resultString = resultString.replaceAll("\\\\144", "�");
        resultString = resultString.replaceAll("\\\\145", "‘");
        resultString = resultString.replaceAll("\\\\146", "’");
        resultString = resultString.replaceAll("\\\\147", "“");
        resultString = resultString.replaceAll("\\\\148", "”");
        resultString = resultString.replaceAll("\\\\149", "•");
        resultString = resultString.replaceAll("\\\\150", "–");
        resultString = resultString.replaceAll("\\\\151", "—");
        resultString = resultString.replaceAll("\\\\152", "˜");
        resultString = resultString.replaceAll("\\\\153", "™");
        resultString = resultString.replaceAll("\\\\154", "š");
        resultString = resultString.replaceAll("\\\\155", "›");
        resultString = resultString.replaceAll("\\\\156", "œ");
        resultString = resultString.replaceAll("\\\\157", "�");
        resultString = resultString.replaceAll("\\\\158", "ž");
        resultString = resultString.replaceAll("\\\\159", "Ÿ");
        resultString = resultString.replaceAll("\\\\160", "¿");

        //resultString = resultString.replaceAll("\\\\160", "");

        resultString = resultString.replaceAll("\\\\161", "¡");
        resultString = resultString.replaceAll("\\\\162", "¢");
        resultString = resultString.replaceAll("\\\\163", "£");
        resultString = resultString.replaceAll("\\\\164", "¤");
        resultString = resultString.replaceAll("\\\\165", "¥");
        resultString = resultString.replaceAll("\\\\166", "¦");
        resultString = resultString.replaceAll("\\\\167", "§");
        resultString = resultString.replaceAll("\\\\168", "¨");
        resultString = resultString.replaceAll("\\\\169", "©");
        resultString = resultString.replaceAll("\\\\170", "ª");
        resultString = resultString.replaceAll("\\\\171", "«");
        resultString = resultString.replaceAll("\\\\172", "¬");
        resultString = resultString.replaceAll("\\\\173", "-");
        resultString = resultString.replaceAll("\\\\174", "®");
        resultString = resultString.replaceAll("\\\\175", "¯");
        resultString = resultString.replaceAll("\\\\176", "°");
        resultString = resultString.replaceAll("\\\\177", "±");
        resultString = resultString.replaceAll("\\\\178", "²");
        resultString = resultString.replaceAll("\\\\179", "³");
        resultString = resultString.replaceAll("\\\\180", "´");
        resultString = resultString.replaceAll("\\\\181", "µ");
        resultString = resultString.replaceAll("\\\\182", "¶");
        resultString = resultString.replaceAll("\\\\183", "·");
        resultString = resultString.replaceAll("\\\\184", "¸");
        resultString = resultString.replaceAll("\\\\185", "¹");
        resultString = resultString.replaceAll("\\\\186", "º");
        resultString = resultString.replaceAll("\\\\187", "»");
        resultString = resultString.replaceAll("\\\\188", "¼");
        resultString = resultString.replaceAll("\\\\189", "½");
        resultString = resultString.replaceAll("\\\\190", "¾");
        resultString = resultString.replaceAll("\\\\191", "¿");
        //

        resultString = resultString.replaceAll("\\\\192", "À");
        resultString = resultString.replaceAll("\\\\193", "Á");
        resultString = resultString.replaceAll("\\\\194", "Â");
        resultString = resultString.replaceAll("\\\\195", "Ã");
        resultString = resultString.replaceAll("\\\\196", "Ä");
        resultString = resultString.replaceAll("\\\\197", "Å");
        resultString = resultString.replaceAll("\\\\198", "Æ");
        resultString = resultString.replaceAll("\\\\199", "Ç");
        resultString = resultString.replaceAll("\\\\200", "È");
        resultString = resultString.replaceAll("\\\\201", "É");
        resultString = resultString.replaceAll("\\\\202", "Ê");
        resultString = resultString.replaceAll("\\\\203", "Ë");
        resultString = resultString.replaceAll("\\\\204", "Ì");
        resultString = resultString.replaceAll("\\\\205", "Í");
        resultString = resultString.replaceAll("\\\\206", "Î");
        resultString = resultString.replaceAll("\\\\207", "Ï");
        resultString = resultString.replaceAll("\\\\208", "Ð");
        resultString = resultString.replaceAll("\\\\209", "Ñ");
        resultString = resultString.replaceAll("\\\\210", "Ò");
        resultString = resultString.replaceAll("\\\\211", "Ó");
        resultString = resultString.replaceAll("\\\\212", "Ô");
        resultString = resultString.replaceAll("\\\\213", "Õ");
        resultString = resultString.replaceAll("\\\\214", "Ö");
        resultString = resultString.replaceAll("\\\\215", "×");
        resultString = resultString.replaceAll("\\\\216", "Ø");
        resultString = resultString.replaceAll("\\\\217", "Ù");
        resultString = resultString.replaceAll("\\\\218", "Ú");
        resultString = resultString.replaceAll("\\\\219", "Û");
        resultString = resultString.replaceAll("\\\\220", "Ü");
        resultString = resultString.replaceAll("\\\\221", "Ý");
        resultString = resultString.replaceAll("\\\\222", "Þ");
        resultString = resultString.replaceAll("\\\\223", "ß");
        resultString = resultString.replaceAll("\\\\224", "à");
        resultString = resultString.replaceAll("\\\\225", "á");
        resultString = resultString.replaceAll("\\\\226", "â");
        resultString = resultString.replaceAll("\\\\227", "ã");
        resultString = resultString.replaceAll("\\\\228", "ä");
        resultString = resultString.replaceAll("\\\\229", "å");
        resultString = resultString.replaceAll("\\\\230", "æ");
        resultString = resultString.replaceAll("\\\\231", "ç");
        resultString = resultString.replaceAll("\\\\232", "è");
        resultString = resultString.replaceAll("\\\\233", "é");
        resultString = resultString.replaceAll("\\\\234", "ê");
        resultString = resultString.replaceAll("\\\\235", "ë");
        resultString = resultString.replaceAll("\\\\236", "ì");
        resultString = resultString.replaceAll("\\\\237", "í");
        resultString = resultString.replaceAll("\\\\238", "î");
        resultString = resultString.replaceAll("\\\\239", "ï");
        resultString = resultString.replaceAll("\\\\240", "ð");
        resultString = resultString.replaceAll("\\\\241", "ñ");
        resultString = resultString.replaceAll("\\\\242", "ò");
        resultString = resultString.replaceAll("\\\\243", "ó");
        resultString = resultString.replaceAll("\\\\244", "ô");
        resultString = resultString.replaceAll("\\\\245", "õ");
        resultString = resultString.replaceAll("\\\\246", "ö");
        resultString = resultString.replaceAll("\\\\247", "÷");
        resultString = resultString.replaceAll("\\\\248", "ø");
        resultString = resultString.replaceAll("\\\\249", "ù");
        resultString = resultString.replaceAll("\\\\250", "ú");
        resultString = resultString.replaceAll("\\\\251", "û");
        resultString = resultString.replaceAll("\\\\252", "ü");
        resultString = resultString.replaceAll("\\\\253", "ý");
        resultString = resultString.replaceAll("\\\\254", "þ");
        resultString = resultString.replaceAll("\\\\255", "ÿ");
        //
        //

        resultString = resultString.replaceAll("\\\\\\+", "&");
        //
        return resultString;
    }
}
