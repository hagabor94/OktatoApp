package hu.hajasgabor.oktatoapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class DataAdapter {

    private final Context mContext;
    private SQLiteDatabase mDb;
    private QuestionsOpenHelper mDbHelper;

    public DataAdapter(Context context) {
        this.mContext = context;
        mDbHelper = new QuestionsOpenHelper(mContext);
    }

    public DataAdapter createDatabase() throws SQLException {
        try {
            mDbHelper.createDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToCreateDatabase");
        }
        return this;
    }

    public DataAdapter open() throws SQLException {
        try {
            mDbHelper.openDatabase();
            mDbHelper.close();
            mDb = mDbHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
        return this;
    }

    public void close() {
        mDbHelper.close();
    }

    public Cursor getQuestionsData() {
        try {
            String sql = "SELECT * FROM questionsdata";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public Cursor getPupilsDataForParents(String username) {
        try {
            String sql = "SELECT pupils.name, pupils.solved_tests, pupils.avg_points FROM pupils INNER JOIN parents WHERE pupils.parent = parents._id AND (SELECT _id FROM parents WHERE username = '" + username + "') = pupils.parent";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public Cursor getPupilsDataForTeachers(String username) {
        try {
            String sql = "SELECT pupils.name, pupils.solved_tests, pupils.avg_points, parents.name AS parent_name FROM pupils INNER JOIN parents INNER JOIN teachers INNER JOIN teacher_pupil ON pupils.parent = parents._id AND  teacher_pupil.pupil_id = pupils._id AND teacher_pupil.teacher_id = teachers._id and teachers.username = '"+username+"'";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public Cursor getOnePupilData(String username) {
        try {
            String sql = "SELECT * FROM pupils WHERE username = '" + username + "'";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null) {
                mCur.moveToNext();
            }
            return mCur;
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public void UpdatePupilQuizResult(String username, int points) {
        Cursor pupil = getOnePupilData(username);
        int solvedTests = pupil.getInt(pupil.getColumnIndexOrThrow("solved_tests"));
        float avgPoints = pupil.getFloat(pupil.getColumnIndexOrThrow("avg_points"));

        avgPoints = (avgPoints * solvedTests + (float)points) / (float)++solvedTests;

        ContentValues cv = new ContentValues();
        cv.put("solved_tests", solvedTests);
        cv.put("avg_points", avgPoints);
        mDb.update("pupils", cv, "username = ?", new String[]{username});
    }

    public boolean UpdatePupilPassword(String username, String oldPwd, String newPwd) throws NoSuchAlgorithmException {
        String table = "pupils";
        if (LoginUser(table, username, oldPwd)) {
            UpdatePassword(table, username, newPwd);
            return true;
        }
        return false;
    }

    public boolean UpdateParentPassword(String username, String oldPwd, String newPwd) throws NoSuchAlgorithmException {
        String table = "parents";
        if (LoginUser(table, username, oldPwd)) {
            UpdatePassword(table, username, newPwd);
            return true;
        }
        return false;
    }

    public boolean UpdateTeacherPassword(String username, String oldPwd, String newPwd) throws NoSuchAlgorithmException {
        String table = "teachers";
        if (LoginUser(table, username, oldPwd)) {
            UpdatePassword(table, username, newPwd);
            return true;
        }
        return false;
    }

    private void UpdatePassword(String table, String username, String password) throws NoSuchAlgorithmException {
        String salt = Utility.GenerateSalt();
        String hashedPwd = Utility.HashPassword(password, salt);
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", hashedPwd);
        contentValues.put("salt", salt);

        mDb.update(table, contentValues, "username = ?", new String[]{username});
    }

    public boolean LoginPupil(String username, String password) throws NoSuchAlgorithmException {
        return LoginUser("pupils", username, password);
    }

    public boolean LoginTeacher(String username, String password) throws NoSuchAlgorithmException {
        return LoginUser("teachers", username, password);
    }

    public boolean LoginParent(String username, String password) throws NoSuchAlgorithmException {
        return LoginUser("parents", username, password);
    }

    private boolean LoginUser(String table, String username, String password) throws NoSuchAlgorithmException {
        try {
            String sql = "SELECT * FROM " + table + " WHERE username='" + username + "'";
            Cursor mCur = mDb.rawQuery(sql, null);
            if (mCur != null && mCur.getCount() != 0) {
                mCur.moveToFirst();
                String salt = mCur.getString(3);
                String hashedPassword = Utility.HashPassword(password, salt);
                String storedPassword = mCur.getString(2);
                return hashedPassword.equals(storedPassword);
            }
            return false;
        } catch (SQLException mSQLException) {
            return false;
        }
    }

}
