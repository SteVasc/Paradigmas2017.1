package com.example.stefany.paradigmas20171.model.local_database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "organize_app_local_db";
    public static final String TABLE_SUBJECT = "subject_table";
    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String SUBJECT_CODE = "subject_code";
    public static final String SUBJECT_PERIOD = "subject_period";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_SUBJECT + "(" +
                            SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SUBJECT_NAME + " TEXT NOT NULL," +
                            SUBJECT_CODE + " TEXT, " +
                            SUBJECT_PERIOD + " INTEGER NOT NULL);");
        insertRequiredSubjectsValues(sqLiteDatabase);
        insertOptionalSubjectsValues(sqLiteDatabase);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECT);
        onCreate(sqLiteDatabase);
    }

    private void insertRequiredSubjectsValues(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_SUBJECT + " values (" +
                "1,'Cálculo a uma variável', '0000', 1), (" +
                "2,'Matemática Discreta', '0000', 1), (" +
                "3,'Laboratório de Informática', '0000', 1), (" +
                "4,'Introdução a programação', '0000', 1), (" +
                "5,'Teoria Geral da Administração', '0000', 1), (" +
                "6,'Cálculo a Várias Variáveis', '0000', 2), (" +
                "7,'Introdução a teoria da Computação', '0000', 2), (" +
                "8,'Laboratório de Programação', '0000', 2), (" +
                "9,'Algoritmo e Estrutura de Dados', '0000', 2), (" +
                "10,'Fundamentos de Sistemas de Informação', '0000', 2), (" +
                "11,'Álgebra Vetorial e Linear para Computação', '0000', 3), (" +
                "12,'Física para Computação', '0000', 3), (" +
                "13,'Modelagem de Programação Orientada a Objetos', '0000', 3), (" +
                "14,'Fundamentos de Engenharia de Software', '0000', 3), (" +
                "15,'Introdução a Economia', '0000', 3), (" +
                "16,'Psicologia Aplicada a Organizações', '0000', 4), (" +
                "17,'Estatística Exploratória I', '0000', 4), (" +
                "18,'Fundamentos de Banco de Dados', '0000', 4), (" +
                "19,'Processo de Desenvolvimento de Software', '0000', 4), (" +
                "20,'Administração Financeira', '0000', 4), (" +
                "21,'Metodologia de Expressão Técnica e Científica', '0000', 5), (" +
                "22,'Infraestrutura de Hardware', '0000', 5), (" +
                "23,'Gerência de Projetos de Software', '0000', 5), (" +
                "24,'Projetos de Banco de Dados', '0000', 5), (" +
                "25,'Análise e Projeto de Sistemas de Informação', '0000', 5), (" +
                "26,'Redes e Sistemas de Internet', '0000', 6), (" +
                "27,'Projeto de Sistemas Distribuídos', '0000', 6), (" +
                "28,'Empreendedorismo e Legislação', '0000', 6), (" +
                "29,'Interface Homem-Máquina', '0000', 7), (" +
                "30,'Paradigmas de Programação', '0000', 7), (" +
                "31,'Sistemas de Apoio à Decisão', '0000', 7), (" +
                "32,'Aspectos Filisóficos e Sociológicos de Informática', '0000', 8), (" +
                "33,'Infraestrutura de Software', '0000', 8), (" +
                "34,'Análise Organizacional e de Processos', '0000', 8), (" +
                "35,'Segurança e Auditoria de Sistemas de Informação', '0000', 9), (" +
                "36,'Fundamentos de Estratégia Competitiva', '0000', 9), (" +
                "37,'Projeto de Conclusão de Curso', '0000', 10);");
    }
    private void insertOptionalSubjectsValues(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_SUBJECT + " values (" +
                "38,'Tendências Tecnológicas em TIC', '14703', 0), (" +
                "39,'Data Warehousing & Business Intelligence', '14326', 0), (" +
                "40,'Tópicos Avançados em IA (Computação Evolucionaria)', '14024', 0), (" +
                "41,'Introdução a Computação Quântica', '6271', 0), (" +
                "42,'Sistemas Colaborativos', '14325', 0), (" +
                "43,'Tópicos  em Engenharia de Software', '06235', 0), (" +
                "44,'Desenvolvimento de Aplicações para Web', '14125', 0), (" +
                "45,'Desenvolvimento de Aplicações Móveis', '14119', 0), (" +
                "46,'Elementos de Epidemiologia Computacional', '06278', 0), (" +
                "47,'Fundamentos de Autômatos Celulares', '14028', 0), (" +
                "48,'Tópicos Avançados em Redes de Computadores (Internet das Coisas)', '14011', 0), (" +
                "49,'Reconhecimento de Padrões', '14019', 0), (" +
                "50,'Fundamentos de Criptografia', '14705', 0), (" +
                "51,'Tópicos em Modelagem Computacional (Controle de SEDs)', '14036', 0), (" +
                "52,'Tópicos em Otimização', '06277', 0), (" +
                "53,'Modelagem Computacional (Fenômenos Temporais)', '14035', 0), (" +
                "54,'Laboratório de Modelagem', '14029', 0), (" +
                "55,'Tópicos Avançados em Computação de Alto Desempenho', '14037', 0);");
    }
}
