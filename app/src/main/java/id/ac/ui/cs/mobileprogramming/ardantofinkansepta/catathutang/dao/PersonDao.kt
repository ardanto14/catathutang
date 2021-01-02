package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao

import android.content.ContentValues
import android.database.Cursor
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import kotlinx.coroutines.flow.Flow


@Dao
interface PersonDao {

    @Transaction
    @Query("SELECT * FROM person")
    fun getAllPerson(): Flow<List<Person>>

    @Transaction
    @Query("SELECT * FROM person")
    fun getAllPersonList(): List<Person>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(person: Person)

    @Transaction
    @Query("UPDATE person SET value = value + :value WHERE id = :id")
    suspend fun updateValue(id: Int, value: Int)

    @Transaction
    @Delete
    suspend fun delete(person: Person)

    @Transaction
    @Query("DELETE FROM person WHERE id = :id ")
    fun deleteById(id: Int) : Int

    @androidx.room.Transaction
    @Query("SELECT * FROM person")
    fun getAllPersonCursor(): Cursor

    @Transaction
    @Update
    fun updateContentProvider(person: Person?): Int

    @Transaction
    @Insert
    fun insertContentProvider(person: Person?): Long
}