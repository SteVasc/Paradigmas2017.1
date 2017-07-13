package com.example.stefany.paradigmas20171.model.local_database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "organize_app_local_db";
    public static final String TABLE_SUBJECT = "subject_table";
    public static final String SUBJECT_ID = "subject_id";
    public static final String SUBJECT_NAME = "subject_name";
    public static final String SUBJECT_CODE = "subject_code";
    public static final String SUBJECT_SEMESTER = "subject_semester";
    public static final String SUBJECT_SCHEDULE = "subject_schedule";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_SUBJECT + "(" +
                            SUBJECT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                            SUBJECT_NAME + " TEXT NOT NULL," +
                            SUBJECT_CODE + " TEXT, " +
                            SUBJECT_SEMESTER + " INTEGER NOT NULL, " +
                            SUBJECT_SCHEDULE + " TEXT NOT NULL);");
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
                "1,'Cálculo a uma variável', '6438', 1, 'SEG 10-12;QUA 10-12'), (" +
                "2,'Matemática Discreta', '6203', 1, 'SEG 08-10;QUA 08-10'), (" +
                "3,'Laboratório de Informática', '6274', 1, 'QUI 10-12'), (" +
                "4,'Introdução a programação', '6236', 1, 'TER 10-12;QUI 08-10;SEX 08-10'), (" +
                "5,'Teoria Geral da Administração', '4166', 1, 'TER 08-10;SEX 10-12'), (" +
                "6,'Cálculo a Várias Variáveis', '6439', 2, 'SEG 10-12;QUA 10-12'), (" +
                "7,'Introdução a teoria da Computação', '6239', 2, 'SEG 08-10;QUA 08-10'), (" +
                "8,'Laboratório de Programação', '6283', 2, 'QUI 08-10;SEX 08-10'), (" +
                "9,'Algoritmo e Estrutura de Dados', '6214', 2, 'TER 10-12;QUI 10-12'), (" +
                "10,'Fundamentos de Sistemas de Informação', '4162', 2, 'TER 08-10;SEX 10-12'), (" +
                "11,'Álgebra Vetorial e Linear para Computação', '6418', 3, 'SEG 10-12;QUA 10-12'), (" +
                "12,'Física para Computação', '6309', 3, 'SEG 08-10;QUA 08-10'), (" +
                "13,'Modelagem de Programação Orientada a Objetos', '6286', 3, 'QUI 08-10;SEX 08-10'), (" +
                "14,'Fundamentos de Engenharia de Software', '6287', 3, 'TER 08-10;TER 10-12'), (" +
                "15,'Introdução a Economia', '4106', 3, 'QUI 10-12;SEX 10-12'), (" +
                "16,'Psicologia Aplicada a Organizações', '5345', 4, 'SEG 08-10;QUA 08-10'), (" +
                "17,'Estatística Exploratória I', '6243', 4, 'SEG 10-12;QUA 10-12'), (" +
                "18,'Fundamentos de Banco de Dados', '6288', 4, 'QUI 08-10;SEX 08-10'), (" +
                "19,'Processo de Desenvolvimento de Software', '6289', 4, 'TER 08-10;TER 10-12'), (" +
                "20,'Administração Financeira', '4163', 4, 'QUI 10-12;SEX 10-12'), (" +
                "21,'Metodologia de Expressão Técnica e Científica', '6295', 5, 'SEG 10-12;QUA 10-12'), (" +
                "22,'Infraestrutura de Hardware', '6246', 5, 'SEG 08-10;QUA 08-10'), (" +
                "23,'Gerência de Projetos de Software', '6296', 5, 'TER 08-10;TER 10-12'), (" +
                "24,'Projetos de Banco de Dados', '6297', 5, 'QUI 08-10;SEX 08-10'), (" +
                "25,'Análise e Projeto de Sistemas de Informação', '14310', 5, 'QUI 10-12;SEX 10-12'), (" +
                "26,'Redes e Sistemas de Internet', '6249', 6, 'SEG 08-10;QUA 08-10'), (" +
                "27,'Projeto de Sistemas Distribuídos', '6298', 6, 'SEG 10-12;QUA 10-12'), (" +
                "28,'Empreendedorismo e Legislação', '4195', 6, 'TER 10-12;SEX 08-10'), (" +
                "29,'Interface Homem-Máquina', '6253', 7, 'SEG 08-10;SEG 10-12'), (" +
                "30,'Paradigmas de Programação', '6252', 7, 'QUI 10-12;SEX 10-12'), (" +
                "31,'Sistemas de Apoio à Decisão', '4236', 7, 'TER 10-12;SEX 08-10'), (" +
                "32,'Aspectos Filisóficos e Sociológicos de Informática', '6258', 8, 'QUA 08-10;SEX 08-10'), (" +
                "33,'Infraestrutura de Software', '6259', 8, 'SEG 08-10;TER 10-12'), (" +
                "34,'Análise Organizacional e de Processos', '4202', 8, 'TER 08-10;QUI 08-10'), (" +
                "35,'Segurança e Auditoria de Sistemas de Informação', '6299', 9, 'SEG 10-12;TER 08-10'), (" +
                "36,'Fundamentos de Estratégia Competitiva', '4203', 9, 'SEG 08-10;TER 10-12'), (" +
                "37,'Projeto de Conclusão de Curso', '14308', 10, '');");
    }
    private void insertOptionalSubjectsValues(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("INSERT INTO " + TABLE_SUBJECT + " values (" +
                "38,'Tendências Tecnológicas em TIC', '14703', 0, 'QUI 10-12;SEX 10-12'), (" +
                "39,'Data Warehousing & Business Intelligence', '14326', 0, 'QUI 10-12;SEX 10-12'), (" +
                "40,'Tópicos Avançados em IA (Computação Evolucionaria)', '14024', 0, 'QUI 10-12;SEX 10-12'), (" +
                "41,'Introdução a Computação Quântica', '6271', 0, 'QUI 10-12;SEX 10-12'), (" +
                "42,'Sistemas Colaborativos', '14325', 0, 'TER 08-10;QUI 08-10'), (" +
                "43,'Tópicos  em Engenharia de Software', '06235', 0, 'QUA 08-10;QUA 10-12'), (" +
                "44,'Desenvolvimento de Aplicações para Web', '14125', 0, 'QUA 08-10;QUA 10-12'), (" +
                "45,'Desenvolvimento de Aplicações Móveis', '14119', 0, 'QUA 08-10;QUA 10-12'), (" +
                "46,'Elementos de Epidemiologia Computacional', '06278', 0, 'QUA 08-10; QUA 10-12'), (" +
                "47,'Fundamentos de Autômatos Celulares', '14028', 0, 'QUA 08-10;QUA 10-12'), (" +
                "48,'Tópicos Avançados em Redes de Computadores (Internet das Coisas)', '14011', 0, 'QUA 08-10;QUA 10-12'), (" +
                "49,'Reconhecimento de Padrões', '14019', 0, 'SEG 10-12;QUA 10-12'), (" +
                "50,'Fundamentos de Criptografia', '14705', 0, 'SEG 10-12; QUA 10-12'), (" +
                "51,'Tópicos em Modelagem Computacional (Controle de SEDs)', '14036', 0, 'QUI 08-10;QUI 10-12'), (" +
                "52,'Tópicos em Otimização', '06277', 0, 'QUI 08-10; QUI 10-12'), (" +
                "53,'Modelagem Computacional (Fenômenos Temporais)', '14035', 0, 'SEX 08-10;SEX 10-12'), (" +
                "54,'Laboratório de Modelagem', '14029', 0, 'SEG 10-12; QUA 10-12'), (" +
                "55,'Tópicos Avançados em Computação de Alto Desempenho', '14037', 0, 'SEX 08-10;SEX 10-12');");
    }
}
