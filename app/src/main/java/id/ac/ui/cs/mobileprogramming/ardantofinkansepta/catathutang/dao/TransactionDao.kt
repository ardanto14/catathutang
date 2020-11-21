package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao


import android.database.Cursor
import androidx.room.*
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @androidx.room.Transaction
    @Query("SELECT * FROM `transaction`")
    fun getAllTransaction(): Flow<List<Transaction>>

    @androidx.room.Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @androidx.room.Transaction
    @Delete
    suspend fun delete(transaction: Transaction)

    @androidx.room.Transaction
    @Update
    suspend fun update(transaction: Transaction)
}