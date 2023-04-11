package com.example.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "Database.db";
    public int insertCount =1;

    public DBHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create table questions" + "(questionName TEXT primary key,questionNumber TEXT,gameSubject TEXT ,gameMode TEXT ,answer TEXT)");
        MyDB.execSQL("create Table users(email TEXT primary key,username TEXT ,password TEXT)");
        MyDB.execSQL("create table trigoScore(username TEXT primary key,trigoPolarScore INTEGER,trigoMCQScore INTEGER,trigoSUBScore INTEGER,totalTrigoScore INTEGER)");
        MyDB.execSQL("create table calScore(username TEXT primary key,calPolarScore INTEGER,calMCQScore INTEGER,calSUBScore INTEGER,totalCalScore INTEGER)");
        MyDB.execSQL("create table factorScore(username TEXT primary key,factorPolarScore INTEGER,factorMCQScore INTEGER,factorSUBScore INTEGER,totalFactorScore INTEGER)");
        MyDB.execSQL("create table totalScore(username TEXT primary key,totalScore INTEGER)");
        MyDB.execSQL("create table friends(username TEXT ,friendName TEXT,totalScore INTEGER)");
        MyDB.execSQL("create table achievement(username TEXT primary key,achievement1 TEXT,achievement2 TEXT,achievement3 TEXT,achievement4 TEXT,achievement5 TEXT,achievement6 TEXT)");

        if(insertCount ==1)
        {
            insertQuestion(MyDB);
            insertCount+=1;
        }

    }

    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists questions");
        MyDB.execSQL("drop Table if exists users");
        MyDB.execSQL("drop Table if exists trigoScore");
        MyDB.execSQL("drop Table if exists calScore");
        MyDB.execSQL("drop Table if exists factorScore");
        MyDB.execSQL("drop Table if exists friends");
        MyDB.execSQL("drop Table if exists achievement");
    }

    public Boolean insertData(String email,String username, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email",email);
        contentValues.put("username",username);
        contentValues.put("password",password);
        long result = MyDB.insert("users",null,contentValues);
        if (result == -1) {
            return false;
        } else {
            insertNewData(username);
        }
            return true;
    }

    public Boolean insertFriend(String username,String friendName )
    {
        int totalScore;
        String checkFriend = "0";
        String[] friendNameArray=getFriendName(username);
        for (int i = 0; i < friendNameArray.length; i++)
        {
            if(friendName.equals(friendNameArray[i]))
            {
                checkFriend= "1";
            }
        }
        if (checkFriend.equals("1"))
        {
            return false;
        }
        else {
            try {
                SQLiteDatabase MyDB = this.getWritableDatabase();
                Cursor cursor = MyDB.rawQuery("Select * from totalScore where  username = ?", new String[]{friendName});
                cursor.moveToFirst();
                totalScore = cursor.getInt(1);


                ContentValues contentValues = new ContentValues();
                contentValues.put("username", username);
                contentValues.put("friendName", friendName);
                contentValues.put("totalScore", totalScore);
                long result = MyDB.insert("friends", null, contentValues);
                if (result == -1)
                    return false;
                else
                    return true;
            }catch (Exception e) {
                return false;
            }

        }
    }

    public String[] getFriendName(String username)
    {
        int arraySize;
        int arrayIndex=0;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from friends where  username = ?", new String[] {username});
        arraySize=cursor.getCount();
        String friendNameArray[] = new String[arraySize];
        while (cursor.moveToNext()) {
            friendNameArray[arrayIndex] = cursor.getString(1);
            arrayIndex+=1;
        }
        return friendNameArray;
    }

    public String[][] getFriendNameScore(String username)
    {
        int arraySize;
        int arrayIndex=0;
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from friends where  username = ?", new String[] {username});
        arraySize=cursor.getCount();
        String friendNameScoreArray[][] = new String[arraySize][2];
        while (cursor.moveToNext()) {
            friendNameScoreArray[arrayIndex][0] = cursor.getString(1);
            friendNameScoreArray[arrayIndex][1] = cursor.getString(2);
            arrayIndex+=1;
        }


        return friendNameScoreArray;
    }

    public Boolean checkFriendExist(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where  username = ?", new String[] {username});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public Boolean checkEmailExist(String email)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? ", new String[] {email});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }


    public Boolean updatePassword(String email , String password)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        if (password.equals("")) {
           return false;
        } else {
            MyDB.execSQL("UPDATE users SET password = ? WHERE email = ?", new String[]{password, email});
            return true;
        }

    }


    public String[] getAchievement(String username)
    {
        String array[] = new String[6];
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from achievement where username = ? ", new String[] {username});
        cursor.moveToFirst();
        for(int i=0;i<6;i++)
        {
            array[i]=cursor.getString(i+1);
        }
        return array;
    }

    public void setAchievement(String username, String typeName)
    {   SQLiteDatabase MyDB = this.getWritableDatabase();

        if (typeName.equals("Polar"))
        {
            MyDB.execSQL("UPDATE achievement SET achievement1 = ? WHERE username =?" ,new Object[]{"1",username}  );
        }
        else if (typeName.equals("MCQ"))
        {
            MyDB.execSQL("UPDATE achievement SET achievement2 = ? WHERE username =?" ,new Object[]{"1",username}   );
        }
        else if (typeName.equals("SUB"))
        {
            MyDB.execSQL("UPDATE achievement SET achievement3 = ? WHERE username =?" ,new Object[]{"1",username}  );
        }
        else if (typeName.equals("Trigo"))
        {
            MyDB.execSQL("UPDATE achievement SET achievement4 = ? WHERE username =?" ,new Object[]{"1",username}  );
        }
        else if (typeName.equals("Cal"))
        {
            MyDB.execSQL("UPDATE achievement SET achievement5 = ? WHERE username =?" ,new Object[]{"1",username}   );
        }
        else if (typeName.equals("Factor"))
        {
            MyDB.execSQL("UPDATE achievement SET achievement6 = ? WHERE username =?" ,new Object[]{"1",username}   );
        }
    }



    public void insertNewData(String username)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("INSERT INTO "+"trigoScore"+"(username,trigoPolarScore,trigoMCQScore,trigoSUBScore,totalTrigoScore) VALUES ('"+username+"',0,0,0,0)");
        MyDB.execSQL("INSERT INTO "+"calScore"+"(username,calPolarScore,calMCQScore,calSUBScore,totalCalScore) VALUES ('"+username+"',0,0,0,0)");
        MyDB.execSQL("INSERT INTO "+"factorScore"+"(username,factorPolarScore,factorMCQScore,factorSUBScore,totalFactorScore) VALUES ('"+username+"',0,0,0,0)");
        MyDB.execSQL("INSERT INTO "+"totalScore"+"(username,totalScore) VALUES ('"+username+"',0)");
        MyDB.execSQL("INSERT INTO "+"achievement"+"(username,achievement1,achievement2,achievement3,achievement4,achievement5,achievement6) VALUES ('"+username+"','0','0','0','0','0','0')");

    }

    public Boolean checkEmailUsername(String email,String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where email = ? and username = ?", new String[] {email,username});
        Log.d("CursorCount",String.valueOf(cursor.getCount()));
        if (cursor.getCount()>0) {

            return true;
        }

        else
            return false;
    }





    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?", new String[]{username,password});
        if(cursor.getCount()>0){
            return true;
        }
        else{
            return false;
        }
    }



    public void updateScore(String username,String gameSubject,String gameMode,int Score)
    {
        int tableScore ,polarScore ,mcqScore,subScore ,totalSubjectScore;;
        if(gameSubject.equals("Trigo"))
        {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            if(gameMode.equals("Polar"))
            {
                Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username =?", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(1);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE trigoScore SET trigoPolarScore = ? WHERE username =?",new Object[]{Score,username}  );
                }
                cursor.close();

            }
            else if(gameMode.equals("MCQ"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(2);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE trigoScore SET trigoMCQScore = ? WHERE username =?",new Object[]{Score,username}  );
                }
                cursor.close();

            }
            else if(gameMode.equals("SUB"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(3);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE trigoScore SET trigoSUBScore = ? WHERE username =?",new Object[]{Score,username}   );
                }
                cursor.close();

            }

            Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username = ?  ", new String[] {username});
            cursor.moveToFirst();
            polarScore=cursor.getInt(1);
            mcqScore=cursor.getInt(2);
            subScore= cursor.getInt(3);
            totalSubjectScore=polarScore+mcqScore+subScore;
            Log.e("test","test");
            if (totalSubjectScore > 12000) {
                setAchievement(username, "Trigo");
            }
            MyDB.execSQL("UPDATE trigoScore SET totalTrigoScore = ? WHERE username = ?", new Object[]{totalSubjectScore, username});
            cursor.close();

        }
        else if(gameSubject.equals("Cal"))
        {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            if(gameMode.equals("Polar"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(1);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE calScore SET calPolarScore = ?WHERE username =?",new Object[]{Score,username} );
                }
                cursor.close();

            }
            else if(gameMode.equals("MCQ"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(2);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE calScore SET calMCQScore = ? WHERE username =?",new Object[]{Score,username}  );
                }
                cursor.close();

            }
            else if(gameMode.equals("SUB"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(3);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE calScore SET calSUBScore = ? WHERE username =?",new Object[]{Score,username}  );
                }
                cursor.close();

            }

            Cursor cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
            cursor.moveToFirst();
            polarScore=cursor.getInt(1);
            mcqScore=cursor.getInt(2);
            subScore= cursor.getInt(3);
            totalSubjectScore=polarScore+mcqScore+subScore;
            if (totalSubjectScore > 12000) {
                setAchievement(username, "Trigo");
            }
            MyDB.execSQL("UPDATE calScore SET totalCalScore = ? WHERE username =?" ,new Object[]{totalSubjectScore,username});
            cursor.close();
        }
        else if(gameSubject.equals("Factor"))
        {
            SQLiteDatabase MyDB = this.getWritableDatabase();
            if(gameMode.equals("Polar"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(1);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE factorScore SET factorPolarScore = ? WHERE username =?",new Object[]{Score,username} );
                }
                cursor.close();

            }
            else if(gameMode.equals("MCQ"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(2);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE factorScore SET factorMCQScore = ? WHERE username =?",new Object[]{Score,username} );
                }
                cursor.close();

            }
            else if(gameMode.equals("SUB"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                tableScore = cursor.getInt(3);
                if (Score>tableScore)
                {
                    MyDB.execSQL("UPDATE factorScore SET factorSUBScore = ? WHERE username =?",new Object[]{Score,username} );
                }
                cursor.close();

            }
            Cursor cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
            cursor.moveToFirst();
            polarScore=cursor.getInt(1);
            mcqScore=cursor.getInt(2);
            subScore= cursor.getInt(3);
            totalSubjectScore=polarScore+mcqScore+subScore;
            if (totalSubjectScore > 12000) {
                setAchievement(username, "Cal");
            }
            MyDB.execSQL("UPDATE factorScore SET totalFactorScore = ? WHERE username =?" ,new Object[]{totalSubjectScore,username});
            cursor.close();
        }
        else
        {
            Log.d("Test", "unable to input data");
        }

        //update total score
        updateTotalScore(username);
    }


    public void updateTotalScore(String username)
    {
        int trigoScore,calScore,factorScore,totalScore;
        SQLiteDatabase MyDB = this.getWritableDatabase();

        Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username = ?  ", new String[] {username});
        cursor.moveToFirst();
        trigoScore=cursor.getInt(4);
        cursor.close();

        cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
        cursor.moveToFirst();
        calScore=cursor.getInt(4);
        cursor.close();

        cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
        cursor.moveToFirst();
        factorScore=cursor.getInt(4);
        cursor.close();

        totalScore=trigoScore+calScore+factorScore;
        cursor = MyDB.rawQuery("Select * from totalScore where username = ?  ", new String[] {username});
        MyDB.execSQL("UPDATE totalScore SET totalScore = ? WHERE username =?" ,new Object[]{totalScore,username});
        MyDB.execSQL("UPDATE friends SET totalScore = ? WHERE friendName =?" ,new Object[]{totalScore,username});
        cursor.close();


    }

    public String getTotalScore(String username)
    {
        int totalScore;

        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from totalScore where username = ?  ", new String[] {username});
        cursor.moveToFirst();
        totalScore=cursor.getInt(1);
        return String.valueOf(totalScore);
    }

    public String  getGameModeScore(String username,String gameSubject,String gameMode)
    {
        int gameModeScore =0;
        SQLiteDatabase MyDB = this.getWritableDatabase();

        if(gameSubject.equals("Trigo"))
        {
            if(gameMode.equals("Polar"))
            {
                Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(1);

                cursor.close();

            }
            else if(gameMode.equals("MCQ"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(2);

                cursor.close();

            }
            else if(gameMode.equals("SUB"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from trigoScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(3);

                cursor.close();
            }

        }
        else if(gameSubject.equals("Cal"))
        {

            if(gameMode.equals("Polar"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(1);

                cursor.close();

            }
            else if(gameMode.equals("MCQ"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(2);

                cursor.close();

            }
            else if(gameMode.equals("SUB"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from calScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore= cursor.getInt(3);

                cursor.close();
            }
        }
        else if(gameSubject.equals("Factor"))
        {
            if(gameMode.equals("Polar"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(1);

                cursor.close();

            }
            else if(gameMode.equals("MCQ"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(2);

                cursor.close();

            }
            else if(gameMode.equals("SUB"))
            {

                Cursor cursor = MyDB.rawQuery("Select * from factorScore where username = ?  ", new String[] {username});
                cursor.moveToFirst();
                gameModeScore = cursor.getInt(3);

                cursor.close();

            }

        }
        else
        {
            Log.d("TestMode", "unable to input data");
        }


        return String.valueOf(gameModeScore);
    }


    public String getFirstQuestionDB(String questionNumber,String gameSubject,String gameMode)
    {


        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from questions where questionNumber = ? and gameSubject = ? and gameMode = ? ", new String[] {questionNumber,gameSubject,gameMode});
        cursor.moveToFirst();
        String questionName = cursor.getString(0);
        cursor.close();


        return String.valueOf(questionName) ;
    }



    public String getQuestionAnswerDB(String questionNumber,String gameSubject,String gameMode)
    {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from questions where questionNumber = ? and gameSubject = ? and gameMode = ? ", new String[] {questionNumber,gameSubject,gameMode});

        cursor.moveToFirst();
        String questionAnswer = cursor.getString(4);
        cursor.close();

        return String.valueOf(questionAnswer) ;
    }

    public void insertQuestion(SQLiteDatabase MyDB)
    {
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('tripolarq1','1','Trigo','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('tripolarq2','2','Trigo','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('tripolarq3','3','Trigo','Polar','2')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('tripolarq4','4','Trigo','Polar','2')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('tripolarq5','5','Trigo','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trimcqq1','1','Trigo','MCQ','A')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trimcqq2','2','Trigo','MCQ','B')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trimcqq3','3','Trigo','MCQ','D')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trimcqq4','4','Trigo','MCQ','B')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trimcqq5','5','Trigo','MCQ','C')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trisubq1','1','Trigo','SUB','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trisubq2','2','Trigo','SUB','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trisubq3','3','Trigo','SUB','6cm')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trisubq4','4','Trigo','SUB','3/4')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('trisubq5','5','Trigo','SUB','cos2B')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calpolarq1','1','Cal','Polar','2')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calpolarq2','2','Cal','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calpolarq3','3','Cal','Polar','2')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calpolarq4','4','Cal','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calpolarq5','5','Cal','Polar','2')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calmcqq1','1','Cal','MCQ','B')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calmcqq2','2','Cal','MCQ','D')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calmcqq3','3','Cal','MCQ','C')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calmcqq4','4','Cal','MCQ','C')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calmcqq5','5','Cal','MCQ','B')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calsubq1','1','Cal','SUB','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calsubq2','2','Cal','SUB','24')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calsubq3','3','Cal','SUB','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calsubq4','4','Cal','SUB','8')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('calsubq5','5','Cal','SUB','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facpolarq1','1','Factor','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facpolarq2','2','Factor','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facpolarq3','3','Factor','Polar','2')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facpolarq4','4','Factor','Polar','1')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facpolarq5','5','Factor','Polar','2')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facmcqq1','1','Factor','MCQ','D')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facmcqq2','2','Factor','MCQ','A')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facmcqq3','3','Factor','MCQ','A')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facmcqq4','4','Factor','MCQ','C')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facmcqq5','5','Factor','MCQ','C')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facsubq1','1','Factor','SUB','(3x-2)(2y-3)')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facsubq2','2','Factor','SUB','(x+2)(x+y)')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facsubq3','3','Factor','SUB','(a-1)(b-1)')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facsubq4','4','Factor','SUB','(x+y+z)(x+1)')");
        MyDB.execSQL("INSERT INTO "+"questions"+"(questionName,questionNumber,gameSubject,gameMode,answer) VALUES ('facsubq5','5','Factor','SUB','(x+4)^2')");

    }




}
