package dbd4topostgres.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

public final class DBDesignerParser {

    private HashMap<String, String> datatypes = null;
    private HashMap<String, String> columns = null;
    private HashMap<String, String> tablesHashMap = null;
    private Iterator<Element> tableElementsPriorized = null;
    private Element documentRoot;

    public DBDesignerParser(String fileName)
            throws FileNotFoundException, Exception {
        Document document = XMLParser.getDocument(new InputSource(new FileReader(new File(fileName))));
        this.documentRoot = document.getDocumentElement();

        this.createHashMapDatatypes(this.documentRoot);

        Element metadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element tables = XMLParser.getUniqueChild(metadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(tables, "TABLE");
        this.createHashMapTables(iteratorTables);
    }

    public Collection<String> getTables() {
        return this.tablesHashMap.values();
    }

    public HashMap<String, String> getColumnsDataTypes() {
        HashMap<String, String> mapColumnsTypes = new HashMap();
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementTables = XMLParser.getUniqueChild(elementMetadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(elementTables, "TABLE");
        Element elementTable = null;
        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            this.createTableColumnsDataTypes(elementTable, mapColumnsTypes);
        }
        return mapColumnsTypes;

    }

    private void createTableColumnsDataTypes(Element elementTable, HashMap<String, String> mapColumnsTypes) {
        StringBuilder sb = new StringBuilder();
        Element elementColumns = XMLParser.getUniqueChild(elementTable, "COLUMNS");
        Iterator iteratorColumns = XMLParser.getChildrenByTagName(elementColumns, "COLUMN");
        String idDatatype = null;
        String datatypeName = null;
        String datatypeParams = null;
        Element elementColumn = null;
        String columnDataType = null;
        while (iteratorColumns.hasNext()) {
            elementColumn = (Element) iteratorColumns.next();
            idDatatype = elementColumn.getAttribute("idDatatype");
            datatypeName = this.datatypes.get(idDatatype) != null ? (String) this.datatypes.get(idDatatype) : "";
            datatypeName = DBDesignerModel4.normalizeAttribute(datatypeName);
            datatypeParams = elementColumn.getAttribute("DatatypeParams");
            datatypeParams = DBDesignerModel4.normalizeAttribute(datatypeParams);
            columnDataType = datatypeName + datatypeParams;
            //columnDataType = columnDataType.replaceAll(" ", "");
            if (columnDataType.length() > 0) {
                mapColumnsTypes.put(columnDataType, columnDataType);
            }

        }

    }

    public void createHashMapDatatypes(Element documentRoot) {
        this.datatypes = new HashMap();
        documentRoot = XMLParser.getUniqueChild(documentRoot, "SETTINGS");
        documentRoot = XMLParser.getUniqueChild(documentRoot, "DATATYPES");
        Iterator iteratorDataTypes = XMLParser.getChildrenByTagName(documentRoot, "DATATYPE");
        Element elementDataType = null;
        while (iteratorDataTypes.hasNext()) {
            elementDataType = (Element) iteratorDataTypes.next();
            String id = elementDataType.getAttribute("ID");
            String typeName = elementDataType.getAttribute("TypeName");
            this.datatypes.put(id, typeName);
        }
    }

    public void createHashMapTables(Iterator iteratorTables) {
        this.tablesHashMap = new HashMap();
        this.columns = new HashMap();
        Element elementTable = null;
        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            this.tablesHashMap.put(elementTable.getAttribute("ID"), DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename")));
            this.addColumns(elementTable);
        }
    }

    private void addColumns(Element elementTable) {
        elementTable = XMLParser.getUniqueChild(elementTable, "COLUMNS");
        Iterator iteratorColumns = XMLParser.getChildrenByTagName(elementTable, "COLUMN");
        String colName = "";
        Element elementColumn = null;
        while (iteratorColumns.hasNext()) {
            elementColumn = (Element) iteratorColumns.next();
            colName = DBDesignerModel4.normalizeAttribute(elementColumn.getAttribute("ColName"));
            colName = colName.replaceAll(" ", "_");
            this.columns.put(elementColumn.getAttribute("ID"), colName);
        }
    }



    public String sqlCreateTable(Set<String> filterSelectedTables, HashMap<String, String> mapCamposTraducao, String tableOwner, String descricaoOID, boolean isCreateTableSelected, boolean isAddCommentsSelected, boolean isDropTableSelected) {
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementTables = XMLParser.getUniqueChild(elementMetadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(elementTables, "TABLE");
        StringBuilder sb = new StringBuilder();
        String tableName = "";
        Element elementTable = null;
        String tableComments = null;
        LinkedHashMap<String, String> mapColumnsComments = new LinkedHashMap<String, String>();

        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            tableName = DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename"));
            tableComments = DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Comments"));
            mapColumnsComments.clear();
            if ((filterSelectedTables.isEmpty()) || (filterSelectedTables.contains(tableName))) {
                if (isDropTableSelected) {
                    sb.append("DROP TABLE IF EXISTS ").append(tableName).append(";\r\n");
                }
                if (isCreateTableSelected || isAddCommentsSelected) {
                    if (isCreateTableSelected) {
                        sb.append("CREATE TABLE ").append(tableName).append("(\r\n");
                    }
                    sb.append(this.sqlColumns(elementTable, mapCamposTraducao, isCreateTableSelected, isAddCommentsSelected, mapColumnsComments));
                    if (isCreateTableSelected) {
                        sb.append("\r\n)");
                        if ((descricaoOID != null) && (!descricaoOID.trim().equals(""))) {
                            sb.append(descricaoOID);
                        }
                        sb.append(";\r\n");
                        if ((tableOwner != null) && (!tableOwner.trim().equals(""))) {
                            sb.append("ALTER TABLE ").append(tableName).append(" OWNER TO ").append(tableOwner.trim()).append(";\r\n");
                        }
                        sb.append("\r\n");
                    }
                    // Adiciona o comentario da tabela
                    if (isAddCommentsSelected) {
                        if ((tableComments != null) && (!tableComments.trim().equals(""))) {
                            sb.append("COMMENT ON TABLE ").append(tableName).append(" IS \'").append(tableComments).append("\';\r\n");
                        }
                        Iterator columnsCommentsIterator = mapColumnsComments.entrySet().iterator();
                        for (Entry coluna : mapColumnsComments.entrySet()) {
                            sb.append("COMMENT ON COLUMN ").append(tableName).append(".").append(coluna.getKey()).append(" IS \'").append(coluna.getValue()).append("\';\r\n");
                        }
                        sb.append("\r\n");
                    }
                }
            }
        }

        return sb.toString();
    }

    public String sqlCreateTableStandardInserts(Set<String> filterSelectedTables) {
        Iterator iteratorTables = this.getElementTablesIteratorPriorized();
        StringBuilder sb = new StringBuilder();
        Element elementTable = null;
        String standardInsertValue = null;
        String tableName = null;


        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();

            if ((filterSelectedTables.isEmpty()) || (filterSelectedTables.contains(DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename"))))) {
                standardInsertValue = DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("StandardInserts")).trim();
                if (standardInsertValue.length() > 0) {
                    sb.append(standardInsertValue);
                    sb.append("\r\n\r\n");
                }

            }
        }

        return sb.toString();
    }

    private String sqlColumns(Element elementTable, HashMap<String, String> mapCamposTraducao, boolean isCreateTableSelected, boolean isAddCommentsSelected, LinkedHashMap<String, String> mapColumnsComments) {
        StringBuilder sb = new StringBuilder();
        Element elementColumns = XMLParser.getUniqueChild(elementTable, "COLUMNS");
        Iterator iteratorColumns = XMLParser.getChildrenByTagName(elementColumns, "COLUMN");
        String listColumnsString = null;
        String primaryKeys = "";
        String idDatatype = null;
        String columnName = null;
        String datatypeName = null;
        String datatypeParams = null;
        String nullOption = null;


        Element elementColumn = null;
        String columnDataType = null;
        String traducaoColuna = null;
        String defaultValue = null;
        String columnComments = null;
        while (iteratorColumns.hasNext()) {
            elementColumn = (Element) iteratorColumns.next();
            idDatatype = elementColumn.getAttribute("idDatatype");
            columnName = DBDesignerModel4.normalizeAttribute(elementColumn.getAttribute("ColName"));
            columnName = columnName.replaceAll(" ", "_");
            columnComments = DBDesignerModel4.normalizeAttribute(elementColumn.getAttribute("Comments"));
            if (isAddCommentsSelected && (columnComments != null) && (!columnComments.trim().equals(""))) {
                mapColumnsComments.put(columnName, columnComments);
            }
            if (isCreateTableSelected) {
                // Trata de montar o tipo da coluna
                datatypeName = this.datatypes.get(idDatatype) != null ? (String) this.datatypes.get(idDatatype) : "";
                datatypeName = DBDesignerModel4.normalizeAttribute(datatypeName);

                datatypeParams = elementColumn.getAttribute("DatatypeParams");
                datatypeParams = DBDesignerModel4.normalizeAttribute(datatypeParams);

                columnDataType = datatypeName + datatypeParams;
                //columnDataType = columnDataType.replaceAll(" ", "");


                // Verifica se usuário alterou a tradução do tipo de dado
                traducaoColuna = mapCamposTraducao.get(columnDataType);
                if ((traducaoColuna != null) && (!traducaoColuna.trim().equals(""))) {
                    columnDataType = traducaoColuna;
                }
                //
                // Obtem o valor default da coluna
                defaultValue = DBDesignerModel4.normalizeAttribute(elementColumn.getAttribute("DefaultValue"));
                if (defaultValue != null && (!defaultValue.trim().equals(""))) {
                    defaultValue = " DEFAULT " + defaultValue;

                }
                //
                nullOption = "NULL";
                if (elementColumn.getAttribute("NotNull").equals(DBDesignerModel4.TRUE)) {
                    nullOption = "NOT NULL";
                }
                //


                if (elementColumn.getAttribute("PrimaryKey").equals(DBDesignerModel4.TRUE)) {
                    primaryKeys = primaryKeys + "," + columnName;
                }
                sb.append(",\r\n\t").append(columnName).append(" ").append(columnDataType).append(defaultValue).append(" ").append(nullOption);
            }

        }
        if (isCreateTableSelected) {
            listColumnsString = sb.toString().substring(3);
            if (primaryKeys.length() > 0) {
                listColumnsString = listColumnsString + ",\r\n\tPRIMARY KEY(" + primaryKeys.substring(1) + ")";
            }
            return listColumnsString;
        }

        return "";

    }

    public String sqlCreateForeingKey(Set<String> filterSelectedTables, boolean withRelationName) {
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementRelations = XMLParser.getUniqueChild(elementMetadata, "RELATIONS");
        Iterator iteratorRelations = XMLParser.getChildrenByTagName(elementRelations, "RELATION");

        StringBuilder sb = new StringBuilder();
        Element elementRelation = null;
        String destIdTable = null;
        String srcIdTable = null;
        String relationName = null;
        String fKFieldsSrcDest = null;
        StringTokenizer tokensFieldsOrigenDestino = null;
        String fieldName = null;
        String referenceDefinitions = null;
        StringTokenizer tokensReferenceDefinitions = null;
        String matching = null;
        String onDelete = null;
        String onDeleteValues[] = null;
        String onUpdate = null;
        String onUpdateValues[] = null;

        while (iteratorRelations.hasNext()) {
            elementRelation = (Element) iteratorRelations.next();

            destIdTable = elementRelation.getAttribute("DestTable");
            srcIdTable = elementRelation.getAttribute("SrcTable");
            relationName = DBDesignerModel4.normalizeAttribute(elementRelation.getAttribute("RelationName"));


            if ((filterSelectedTables.isEmpty()) || (filterSelectedTables.contains(this.tablesHashMap.get(destIdTable)))) {
                //
                // Obtem colunas origens e destinos
                // formato da colunas : col_src1=col_dest1\ncol_src2=col_dest2\ncol_src3=col_dest3\n
                //
                fKFieldsSrcDest = DBDesignerModel4.normalizeAttribute(elementRelation.getAttribute("FKFields"));
                tokensFieldsOrigenDestino = new StringTokenizer(fKFieldsSrcDest, "\r\n");

                sb.append("ALTER TABLE ").append((String) this.tablesHashMap.get(destIdTable));
                // Se for nomear o relacionamento
                if (withRelationName) {
                    sb.append("\r\n\tADD CONSTRAINT ").append(relationName.replaceAll(" ", "_")).append(" FOREIGN KEY " + "(");
                } else {
                    sb.append("\r\n\tADD FOREIGN KEY " + "(");
                }

                // Adicionar as colunas destinos

                while (tokensFieldsOrigenDestino.hasMoreTokens()) {
                    fieldName = tokensFieldsOrigenDestino.nextToken().split("=")[1];
                    fieldName = fieldName.replaceAll(" ", "_");

                    sb.append(fieldName);
                    if (tokensFieldsOrigenDestino.hasMoreTokens()) {
                        sb.append(", ");
                    } else {
                        sb.append(") ");
                    }

                }
                sb.append("\r\n\tREFERENCES ").append((String) this.tablesHashMap.get(srcIdTable)).append("(");
                // Reestarta os tokens para adicionar as colunas origens
                tokensFieldsOrigenDestino = new StringTokenizer(fKFieldsSrcDest, "\r\n");

                while (tokensFieldsOrigenDestino.hasMoreTokens()) {
                    fieldName = tokensFieldsOrigenDestino.nextToken().split("=")[0];
                    fieldName = fieldName.replaceAll(" ", "_");

                    sb.append(fieldName);
                    if (tokensFieldsOrigenDestino.hasMoreTokens()) {
                        sb.append(", ");
                    } else {
                        sb.append(")");
                    }

                }
                // Adiciona restrições on delete/update
                // Verifica se foi criado a definição
                if (elementRelation.getAttribute("CreateRefDef").equals(DBDesignerModel4.TRUE)) {
                    //Adiciona Referencia On Updade e On Delete
                    referenceDefinitions = DBDesignerModel4.normalizeAttribute(elementRelation.getAttribute("RefDef"));
                    tokensReferenceDefinitions = new StringTokenizer(referenceDefinitions, "\r\n");
                    // Verifica se o tipo de Matchin da definição é full, partil ou simple
                    matching = tokensReferenceDefinitions.nextToken();
                    //String matchingValues[] = matching.split("=");
                    // Só gera referencia obrigatória
                    //if ((matchingValues[0].equals("Matching")) && (matchingValues[1].equals(DBDesignerModel4.RELATION_REFERENCE_DEFINITION))) {
                    // Obtem valores de OnDelete
                    onDelete = tokensReferenceDefinitions.nextToken();
                    onDeleteValues = onDelete.split("=");
                    if (onDeleteValues[0].equals("OnDelete")) {
                        sb.append("\r\n\tON DELETE ");
                        sb.append(DBDesignerModel4.getDescriptionOnDeleteUpdate(onDeleteValues[1]));

                    }


                    // Obtem valores de OnUpdate
                    onUpdate = tokensReferenceDefinitions.nextToken();
                    onUpdateValues = onUpdate.split("=");
                    if (onUpdateValues[0].equals("OnUpdate")) {
                        sb.append("\r\n\tON UPDATE ");
                        sb.append(DBDesignerModel4.getDescriptionOnDeleteUpdate(onUpdateValues[1]));

                    }


                    //}

                }
                //
                sb.append(";\r\n\r\n");
            }
        }
        return sb.toString();
    }

    public String sqlCreateView(Set<String> filterSelectedTables, String tableOwner, boolean isCreateViewSelected, boolean isDropTableSelected) {
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementTables = XMLParser.getUniqueChild(elementMetadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(elementTables, "TABLE");
        StringBuilder sb = new StringBuilder();
        String tableName = "";
        Element elementTable = null;
        String viewName = null;
        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            tableName = DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename"));
            viewName = "";
            if ((filterSelectedTables.isEmpty()) || (filterSelectedTables.contains(tableName))) {




                if (tableName.startsWith("'")) {
                    // Remove aspas finais                     
                    viewName = viewName + "'vi_";
                    if (tableName.endsWith("'")) {
                        viewName = viewName + tableName.substring(1, tableName.length() - 1);
                    } else {
                        viewName = viewName + tableName.substring(1, tableName.length());
                    }
                    viewName = viewName + "'";
                } else {
                    viewName = viewName + "vi_";
                    viewName = viewName + tableName;
                }

                if (isDropTableSelected) {
                    sb.append("DROP VIEW IF EXISTS ").append(viewName).append(";\r\n");
                }

                if (isCreateViewSelected) {
                    sb.append("CREATE VIEW ");
                    sb.append(viewName).append(" ");



                    sb.append("AS \r\n");
                    sb.append("SELECT \r\n");
                    sb.append(this.sqlColumnNames(elementTable));
                    sb.append("FROM ").append(tableName);
                    sb.append(";\r\n");
                    if ((tableOwner != null) && (!tableOwner.trim().equals(""))) {
                        sb.append("ALTER TABLE ").append(viewName).append(" OWNER TO ").append(tableOwner.trim()).append(";");
                    }
                    sb.append("\r\n\r\n");
                }
            }
        }

        return sb.toString();
    }

    private String sqlColumnNames(Element elementTable) {
        StringBuilder sb = new StringBuilder();
        Element elementColumns = XMLParser.getUniqueChild(elementTable, "COLUMNS");
        Iterator iteratorColumns = XMLParser.getChildrenByTagName(elementColumns, "COLUMN");
        Element elementColumn = null;
        String columnName = null;
        while (iteratorColumns.hasNext()) {
            elementColumn = (Element) iteratorColumns.next();
            columnName = DBDesignerModel4.normalizeAttribute(elementColumn.getAttribute("ColName"));
            columnName = columnName.replaceAll(" ", "_");
            sb.append("\t").append(columnName).append(",\r\n");
        }
        String columnNames = sb.toString();
        if (columnNames.length() < 3) {
            return "";
        }
        return columnNames.substring(0, columnNames.length() - 3) + "\r\n";
    }

    public String sqlCreateSequence(Set<String> filterSelectedTables, String tableOwner, boolean isCreateSequenceSelected, boolean isDropTableSelected) {
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementTables = XMLParser.getUniqueChild(elementMetadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(elementTables, "TABLE");
        StringBuilder sb = new StringBuilder();
        String tableName = "";
        Element elementTable = null;
        String sequenceName = null;
        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            String pk = getPKAutoIncrement(elementTable);
            if (pk.length() > 0) {
                tableName = DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename"));
                sequenceName = tableName;
                if ((filterSelectedTables.isEmpty()) || (filterSelectedTables.contains(tableName))) {


                    if (sequenceName.endsWith("'")) {
                        // Remove aspas finais
                        sequenceName = sequenceName.substring(0, sequenceName.length() - 1);
                        sequenceName = sequenceName + "_seq' ";
                    } else {
                        sequenceName = sequenceName + "_seq ";
                    }
                    if (isDropTableSelected) {
                        sb.append("DROP SEQUENCE IF EXISTS ").append(sequenceName).append(";\r\n");
                    }
                    if (isCreateSequenceSelected) {
                        sb.append("CREATE SEQUENCE ");
                        sb.append(sequenceName);

                        sb.append("\r\n\tSTART 1 \r\n\tINCREMENT 1 \r\n\tMAXVALUE  9223372036854775807 \r\n\tMINVALUE 1  \r\n\tCACHE 1 ;\r\n");
                        if ((tableOwner != null) && (!tableOwner.trim().equals(""))) {
                            sb.append("ALTER TABLE ").append(sequenceName).append(" OWNER TO ").append(tableOwner.trim()).append(";\r\n");
                        }
                        sb.append("\r\n");
                    }
                }
            }
        }
        return sb.toString();
    }

    public String sqlSetDefault(Set<String> filterSelectedTables) {
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementTables = XMLParser.getUniqueChild(elementMetadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(elementTables, "TABLE");
        StringBuilder sb = new StringBuilder();
        String tableName = "";
        Element elementTable = null;
        String primaryKeyName = null;
        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            tableName = DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename"));
            if ((filterSelectedTables.isEmpty()) || (filterSelectedTables.contains(tableName))) {
                primaryKeyName = this.getPKAutoIncrement(elementTable);
                if (primaryKeyName.length() > 0) {
                    sb.append("ALTER TABLE ");
                    sb.append(tableName);
                    sb.append(" ALTER COLUMN ").append(primaryKeyName).append(" SET DEFAULT nextval('");
                    if (tableName.startsWith("'")) {
                        if (tableName.endsWith("'")) {
                            // Remove aspas inicial e final                            
                            sb.append(tableName.substring(1, tableName.length() - 1));
                        } else {
                            // Remove aspa inicial  
                            sb.append(tableName.substring(1, tableName.length()));
                        }
                    } else if (tableName.endsWith("'")) {
                        // Remove aspa final  
                        sb.append(tableName.substring(0, tableName.length() - 1));
                    } else {
                        // Não tem aspas
                        sb.append(tableName);
                    }
                    //

                    sb.append("_seq'::text);\r\n");
                }
            }
        }


        return sb.toString();
    }

    private String getPKAutoIncrement(Element elementTable) {
        Element elementColumns = XMLParser.getUniqueChild(elementTable, "COLUMNS");
        Iterator iteratorColumns = XMLParser.getChildrenByTagName(elementColumns, "COLUMN");
        String primaryKeyName = "";
        Element elementColumn = null;
        while (iteratorColumns.hasNext()) {
            elementColumn = (Element) iteratorColumns.next();
            String colName = DBDesignerModel4.normalizeAttribute(elementColumn.getAttribute("ColName"));
            colName = colName.replaceAll(" ", "_");

            if ((elementColumn.getAttribute("PrimaryKey").equals(DBDesignerModel4.TRUE)) && (elementColumn.getAttribute("AutoInc").equals(DBDesignerModel4.TRUE)) && (elementColumn.getAttribute("IsForeignKey").equals(DBDesignerModel4.FALSE))) {
                primaryKeyName = primaryKeyName + "," + colName;
            }
        }
        if (primaryKeyName.length() > 0) {
            return primaryKeyName.substring(1);
        }
        return "";
    }

    public String sqlCreateAlternatingKey(Set<String> filterSelectedTables, boolean isAddAlternateKeySelected, boolean isDropTableSelected) {
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementTables = XMLParser.getUniqueChild(elementMetadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(elementTables, "TABLE");
        StringBuilder sb = new StringBuilder();
        Element elementTable = null;
        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            if ((filterSelectedTables.isEmpty()) || (filterSelectedTables.contains(DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename"))))) {
                sb.append(this.sqlCreateAlternatingKey(elementTable, isAddAlternateKeySelected, isDropTableSelected));
            }
        }
        return sb.toString();
    }

    private String sqlCreateAlternatingKey(Element elementTable, boolean isAddAlternateKeySelected, boolean isDropTableSelected) {
        StringBuilder sb = new StringBuilder();
        Element elementIndices = XMLParser.getUniqueChild(elementTable, "INDICES");
        Iterator iteratorIndeces = XMLParser.getChildrenByTagName(elementIndices, "INDEX");
        Element elementIndex = null;
        Element elementIndexColumns = null;
        Iterator iteratorIndexColumns = null;
        Element elementIndexColumn = null;
        String columnNames = null;
        String columnName = null;
        String indexName = null;
        while (iteratorIndeces.hasNext()) {
            elementIndex = (Element) iteratorIndeces.next();
            if (elementIndex.getAttribute("IndexKind").equals(DBDesignerModel4.INDEX_TYPE_INDEX)
                    || elementIndex.getAttribute("IndexKind").equals(DBDesignerModel4.INDEX_TYPE_UNIQUE_INDEX)
                    || elementIndex.getAttribute("IndexKind").equals(DBDesignerModel4.INDEX_TYPE_FULL_TEXT_INDEX)) {
                indexName = DBDesignerModel4.normalizeAttribute(elementIndex.getAttribute("IndexName"));
                if (isDropTableSelected) {
                    sb.append("DROP INDEX IF EXISTS ").append(indexName).append(";\r\n");
                }
                if (isAddAlternateKeySelected) {
                    if (elementIndex.getAttribute("IndexKind").equals(DBDesignerModel4.INDEX_TYPE_UNIQUE_INDEX)) {
                        sb.append("CREATE UNIQUE INDEX ");
                    } else {
                        sb.append("CREATE INDEX ");
                    }
                    sb.append(indexName);
                    sb.append(" ON ");
                    sb.append(DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename")));
                    sb.append("(");
                    elementIndexColumns = XMLParser.getUniqueChild(elementIndex, "INDEXCOLUMNS");
                    iteratorIndexColumns = XMLParser.getChildrenByTagName(elementIndexColumns, "INDEXCOLUMN");
                    columnNames = "";
                    while (iteratorIndexColumns.hasNext()) {
                        elementIndexColumn = (Element) iteratorIndexColumns.next();
                        columnName = (String) this.columns.get(elementIndexColumn.getAttribute("idColumn"));
                        columnNames = columnNames + ", " + columnName;
                    }
                    sb.append(columnNames.substring(1));
                    sb.append(");\r\n\r\n");
                }
            }
        }
        return sb.toString();
    }

    public Iterator<Element> getElementTablesIteratorPriorized() {

        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementTables = XMLParser.getUniqueChild(elementMetadata, "TABLES");
        Iterator iteratorTables = XMLParser.getChildrenByTagName(elementTables, "TABLE");
        Element elementTable = null;
        TablesPriority tablesPriority = this.getTablesPriority();
        ArrayList arrayTablesPriority = tablesPriority.getTablesPriority();
        LinkedHashMap mapTabelasOrdenadas = new LinkedHashMap();
        String tableName = null;
        for (int i = 0; i < arrayTablesPriority.size(); i++) {
            mapTabelasOrdenadas.put(arrayTablesPriority.get(i), null);
        }
        for (String key : this.tablesHashMap.keySet()) {
            if (mapTabelasOrdenadas.containsKey(key)) {
                mapTabelasOrdenadas.put(key, null);
            }
        }
        // Preenche o map com as tabelas
        while (iteratorTables.hasNext()) {
            elementTable = (Element) iteratorTables.next();
            tableName = DBDesignerModel4.normalizeAttribute(elementTable.getAttribute("Tablename"));
            mapTabelasOrdenadas.put(tableName, elementTable);


        }
        Iterator elementTablesOrdenada = mapTabelasOrdenadas.values().iterator();
        return elementTablesOrdenada;
    }

    private TablesPriority getTablesPriority() {
        Element elementMetadata = XMLParser.getUniqueChild(this.documentRoot, "METADATA");
        Element elementRelations = XMLParser.getUniqueChild(elementMetadata, "RELATIONS");
        Iterator iteratorRelations = XMLParser.getChildrenByTagName(elementRelations, "RELATION");


        Element elementRelation = null;
        String destIdTable = null;
        String srcIdTable = null;
        String destTableName = null;
        String srcTableName = null;
        String relationName = null;
        TablesPriority tablesPriority = new TablesPriority();



        while (iteratorRelations.hasNext()) {
            elementRelation = (Element) iteratorRelations.next();

            destIdTable = elementRelation.getAttribute("DestTable");
            srcIdTable = elementRelation.getAttribute("SrcTable");
            relationName = DBDesignerModel4.normalizeAttribute(elementRelation.getAttribute("RelationName"));



            destTableName = this.tablesHashMap.get(destIdTable);
            srcTableName = this.tablesHashMap.get(srcIdTable);
            tablesPriority.putTablesReference(destTableName, srcTableName);




        }
        return tablesPriority;
    }
}

class TablesPriority {

    ArrayList<String> tables = null;
    ArrayList<TableReference> insertedTableReferences = null;

    public TablesPriority() {
        this.tables = new ArrayList<String>();
        this.insertedTableReferences = new ArrayList<TableReference>();
    }

    public ArrayList<String> getTablesPriority() {
        return this.tables;
    }

    public void putTablesReference(String destinationTableReference, String sourceTableReference) {
        // Verifica se a origem ja esta na lista de prioridades
        TableReference newTableReference = new TableReference(sourceTableReference, destinationTableReference);

        // Verifica se nao incluiu a referencia

        if (!this.insertedTableReferences.contains(newTableReference)) {
            if ((!this.tables.contains(sourceTableReference))) {

                // Verifica se destino nao existe
                if (!this.tables.contains(destinationTableReference)) {
                    this.tables.add(sourceTableReference);
                    this.tables.add(destinationTableReference);
                } else {
                    // Se a tabela de destino ja existir, insere a origem antes de todas as tabelas
                    //                     
                    this.tables.add(this.tables.indexOf(destinationTableReference), sourceTableReference);
                }


            } else {
                // Trata so a inclusao da tabela de destino
                // Se ja existir, mover tornar a tabela origem mais prioritaria 
                if ((!sourceTableReference.equals(destinationTableReference)) && this.tables.contains(destinationTableReference)) {
                    // move source do seu lugar pro lugar da tabela destino, se destino for mais prioritário que origem
                    if (this.tables.indexOf(sourceTableReference) > this.tables.indexOf(destinationTableReference)) {
                        this.tables.remove(sourceTableReference);
                        this.tables.add(this.tables.indexOf(destinationTableReference), sourceTableReference);
                    }
                } else if (!this.tables.contains(destinationTableReference)) {
                    // adiciona a tabela destino de referencia no final da lista
                    this.tables.add(destinationTableReference);
                }
            }

            this.insertedTableReferences.add(newTableReference);
        }

    }
}

class TableReference implements Comparable<TableReference> {

    private String sourceTable = null;
    private String destinationTable = null;

    public TableReference(String sourceTableReference, String destinationTableReference) {
        this.sourceTable = sourceTableReference;
        this.destinationTable = destinationTableReference;
    }

    public String getDestinationTable() {
        return destinationTable;
    }

    public void setDestinationTable(String destinationTable) {
        this.destinationTable = destinationTable;
    }

    public String getSourceTable() {
        return sourceTable;
    }

    public void setSourceTable(String sourceTable) {
        this.sourceTable = sourceTable;
    }

    public int compareTo(TableReference newTableReference) throws NullPointerException {
        if (this.getSourceTable().equals(newTableReference.getSourceTable())) {
            return this.getDestinationTable().compareTo(newTableReference.getDestinationTable());
        }
        return this.getSourceTable().compareTo(newTableReference.getSourceTable());

    }
}
