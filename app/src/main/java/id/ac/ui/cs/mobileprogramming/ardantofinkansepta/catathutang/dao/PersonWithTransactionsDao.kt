package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.PersonWithTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonWithTransactionsDao {
    @Transaction
    @Query("SELECT * FROM person WHERE id = :id")
    fun getPersonWithTranscationsById(id: Int): Flow<PersonWithTransactions>
}