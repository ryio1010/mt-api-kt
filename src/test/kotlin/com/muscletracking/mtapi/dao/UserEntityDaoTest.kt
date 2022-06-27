package com.muscletracking.mtapi.dao

import com.muscletracking.mtapi.dao.user.UserDao
import com.muscletracking.mtapi.entity.user.UserEntity
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations.deleteAllFrom
import com.ninja_squad.dbsetup.Operations.sequenceOf
import com.ninja_squad.dbsetup.destination.DataSourceDestination
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup.operation.Operation
import com.ninja_squad.dbsetup_kotlin.DbSetupBuilder
import com.ninja_squad.dbsetup_kotlin.insertInto
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldNotBeNull
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import javax.sql.DataSource
import javax.xml.crypto.Data


@SpringBootTest
internal class UserEntityDaoTest {

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var userDao: UserDao

    @BeforeEach
    fun setUp() {
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    @DisplayName("ユーザーIDでユーザーを1件取得できる")
    fun selectByIdTest() {
        // db test data setup
        val dest = DataSourceDestination(dataSource)
        val deleteAllUserTable = deleteAllFrom("m_user")
        val insertTestUser = insertInto("m_user") {
            withDefaultValue("regdate", LocalDateTime.now())
            withDefaultValue("upddate", LocalDateTime.now())
            withDefaultValue("version", 1)
            columns("userid", "username", "password", "regid", "updid")
            values("test1", "テストユーザー", "test1", "test1", "test1")
        }
        val ops = sequenceOf(deleteAllUserTable, insertTestUser)
        DbSetup(dest, ops).launch()

        // dbselect
        val actual = userDao.selectById("test1")

        // assertion
        actual.shouldNotBeNull()
        actual.id `should be equal to` "test1"
        actual.userName `should be equal to` "テストユーザー"
        actual.password `should be equal to` "test1"
        actual.regId `should be equal to` "test1"
        actual.regId `should be equal to` "test1"
    }

    @Test
    @DisplayName("新規ユーザー1件登録できる")
    fun insertNewUserTest() {
        val dest = DataSourceDestination(dataSource)
        val deleteAllUserTable = deleteAllFrom("m_user")
        val opt = sequenceOf(deleteAllUserTable)
        DbSetup(dest, opt).launch()

        val newUser: UserEntity =
            UserEntity(id = "test1", userName = "テストユーザー", password = "test1", regId = "test1", updId = "test1")
        val insert = userDao.insertNewUser(newUser)

        insert.entity.id.`should be equal to`("test1")
        insert.entity.userName.`should be equal to`("テストユーザー")
        insert.entity.password.`should be equal to`("test1")
    }

    @Test
    @DisplayName("ユーザー情報を更新できる")
    fun updateUserTest() {
        // db test data setup
        val dest = DataSourceDestination(dataSource)
        val deleteAllUserTable = deleteAllFrom("m_user")
        val insertTestUser = insertInto("m_user") {
            withDefaultValue("regdate", LocalDateTime.now())
            withDefaultValue("upddate", LocalDateTime.now())
            withDefaultValue("version", 1)
            columns("userid", "username", "password", "regid", "updid")
            values("test1", "テストユーザー", "test1", "test1", "test1")
        }
        val ops = sequenceOf(deleteAllUserTable, insertTestUser)
        DbSetup(dest, ops).launch()

        val updateUser =
            UserEntity(id = "test1", userName = "テストユーザー2", password = "updated", regId = "test1", updId = "test1")
        val update = userDao.updateUser(updateUser)

        update.entity.userName.`should be equal to`("テストユーザー2")
        update.entity.password.`should be equal to`("updated")

    }
}