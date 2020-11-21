package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.PersonDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.PersonWithTransactionsDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.TransactionDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction

@Database(entities = [Person::class, Transaction::class], version = 1, exportSchema = false)
public abstract class MainRoomDatabase : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun transactionDao() : TransactionDao
    abstract fun personWithTransactionsDao(): PersonWithTransactionsDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: MainRoomDatabase? = null

        fun getDatabase(context: Context): MainRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainRoomDatabase::class.java,
                    "main_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}