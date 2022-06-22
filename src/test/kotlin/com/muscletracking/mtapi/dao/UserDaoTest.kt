package com.muscletracking.mtapi.dao

import com.muscletracking.mtapi.dao.user.UserDao
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


@SpringBootTest
internal class UserDaoTest {

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
    @DisplayName("test")
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
}