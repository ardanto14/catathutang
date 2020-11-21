package id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.contentprovider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.annotation.Nullable
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.dao.PersonDao
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.database.MainRoomDatabase
import id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.entity.Person


class PersonProvider : ContentProvider() {
    private var personDao: PersonDao? = null

    companion object {
        val TAG = PersonProvider::class.java.name

        /**
         * Authority of this content provider
         */
        const val AUTHORITY = "id.ac.ui.cs.mobileprogramming.ardantofinkansepta.catathutang.contentprovider.personprovider"
        const val TRANSACTION_TABLE_NAME = "person"

        /**
         * The match code for some items in the Person table
         */
        const val ID_PERSON_DATA = 1

        /**
         * The match code for an item in the PErson table
         */
        const val ID_PERSON_DATA_ITEM = 2
        val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY,
                    TRANSACTION_TABLE_NAME,
                    ID_PERSON_DATA)
            uriMatcher.addURI(AUTHORITY,
                    TRANSACTION_TABLE_NAME +
                            "/*", ID_PERSON_DATA_ITEM)
        }
    }

    override fun onCreate(): Boolean {
        personDao = context?.let { MainRoomDatabase.getDatabase(it).personDao() }
        return false
    }

    @Nullable
    override fun query(uri: Uri,
              @Nullable projection: Array<String?>?,
              @Nullable selection: String?,
              @Nullable selectionArgs: Array<String?>?,
              @Nullable sortOrder: String?): Cursor {
        val cursor: Cursor
        when (uriMatcher.match(uri)) {
            ID_PERSON_DATA -> {
                cursor = personDao!!.getAllPersonCursor()
                if (context != null) {
                    cursor.setNotificationUri(context!!
                            .contentResolver, uri)
                    return cursor
                }
                throw IllegalArgumentException("Unknown URI: $uri")
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    @Nullable
    override fun getType(uri: Uri): String? {
        return null
    }

    @Nullable
    override fun insert(uri: Uri,
               @Nullable values: ContentValues?): Uri {
        when (uriMatcher.match(uri)) {
            ID_PERSON_DATA -> {
                if (context != null) {
                    val id: Long = personDao!!.insertContentProvider(Person(values!!.getAsInteger("userId"),
                            values!!.getAsString("name"),
                            values!!.getAsInteger("initialValue"),
                            values!!.getAsInteger("value")))
                    if (id != 0L) {
                        context!!.contentResolver
                                .notifyChange(uri, null)
                        return ContentUris.withAppendedId(uri, id)
                    }
                }
                throw IllegalArgumentException("Invalid URI: Insert failed$uri")
            }
            ID_PERSON_DATA_ITEM -> throw IllegalArgumentException("Invalid URI: Insert failed$uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri,
               @Nullable selection: String?,
               @Nullable selectionArgs: Array<String?>?): Int {
        when (uriMatcher.match(uri)) {
            ID_PERSON_DATA -> throw IllegalArgumentException("Invalid uri: cannot delete")
            ID_PERSON_DATA_ITEM -> {
                if (context != null) {
                    val count: Int = personDao!!.deleteById(ContentUris.parseId(uri).toInt())
                    context!!.contentResolver
                            .notifyChange(uri, null)
                    return count
                }
                throw IllegalArgumentException("Unknown URI:$uri")
            }
            else -> throw IllegalArgumentException("Unknown URI:$uri")
        }
    }

    override fun update(uri: Uri,
               @Nullable values: ContentValues?,
               @Nullable selection: String?,
               @Nullable selectionArgs: Array<String?>?): Int {
        when (uriMatcher.match(uri)) {
            ID_PERSON_DATA -> {
                if (context != null) {
                    val count: Int = personDao!!
                            .updateContentProvider(Person(values!!.getAsInteger("userId"),
                                    values!!.getAsString("name"),
                            values!!.getAsInteger("initialValue"),
                            values!!.getAsInteger("value")))
                    if (count != 0) {
                        context!!.contentResolver
                                .notifyChange(uri, null)
                        return count
                    }
                }
                throw IllegalArgumentException("Invalid URI:  cannot update")
            }
            ID_PERSON_DATA_ITEM -> throw IllegalArgumentException("Invalid URI:  cannot update")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}