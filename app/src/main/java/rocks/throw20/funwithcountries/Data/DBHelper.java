package rocks.throw20.funwithcountries.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by josel on 3/10/2016.
 *
 */
class DBHelper extends SQLiteOpenHelper {

    // TODO Create scores database
    // The databse version
    private static final int DATABASE_VERSION = 15;

    private static final String DATABASE_NAME = "countries.db";

    public DBHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_COUNTRIES_TABLE  =
                "CREATE TABLE " +
                        Contract.CountryEntry.COUNTRIES_TABLE_NAME + " (" +
                        Contract.CountryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        Contract.CountryEntry.countryId + " INTEGER NOT NULL, " +
                        Contract.CountryEntry.countryName + " TEXT NOT NULL, " +
                        Contract.CountryEntry.countryCapital + " TEXT NULL, " +
                        Contract.CountryEntry.countryRegion + " TEXT NULL, " +
                        Contract.CountryEntry.countrySubRegion + " TEXT NULL, " +
                        Contract.CountryEntry.countryPopulation + " INTEGER NULL " +
                        ")";
        sqLiteDatabase.execSQL(SQL_CREATE_COUNTRIES_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Contract.CountryEntry.COUNTRIES_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
