package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class GreenDao {
    public static final int version = 1;
    private static String srcDir = "../app/src/main/java-gen";

    public static void main(String[] args) {

        Schema schema = new Schema(version, "io.github.cyning.greendao");
        schema.enableKeepSectionsByDefault();//enable Keep sections
        schema.enableActiveEntitiesByDefault();

        Entity hotArticle = schema.addEntity("HotArticle");
        hotArticle.addStringProperty("groupName").codeBeforeField("@SerializedName(\"groupName\")");
        hotArticle.addStringProperty("title");
        hotArticle.addStringProperty("abstractX").codeBeforeField("@SerializedName(\"descrip\")");

        hotArticle.addStringProperty("serNo").codeBeforeField("@SerializedName(\"seriNo\")");;
        hotArticle.addStringProperty("category");
        hotArticle.addStringProperty("link").primaryKey().codeBeforeField("@SerializedName(\"url\")");;
        hotArticle.addStringProperty("imgUrl");
        hotArticle.addStringProperty("updateTime").codeBeforeField("@SerializedName(\"publishTime\")");;
        hotArticle.implementsInterface("Serializable");
        //hotArticle.setSkipGeneration(false);// 不要自动生成，除非有必要



        Entity keyValue = schema.addEntity("Key_Value");
        keyValue.addStringProperty("key");
        keyValue.addStringProperty("value");
        //hotArticle.setSkipGeneration(true);// 不要自动生成，除非有必要


        try {
            DaoGenerator daoGenerator = new DaoGenerator();
            daoGenerator.generateAll(schema, srcDir);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
