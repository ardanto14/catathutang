package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity

import androidx.room.Embedded
import androidx.room.Relation

data class PersonWithTransactions (
    @Embedded
    val person: Person,
    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    val transactions: List<Transaction>
)