package com.muscletracking.mtapi.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.Data
import org.seasar.doma.Column
import org.seasar.doma.Entity
import org.seasar.doma.Transient
import org.seasar.doma.Version
import java.time.LocalDateTime

@Entity(listener = BaseColumnEntityListener::class)
@Data
open class BaseColumnEntity {

    @JsonIgnore
    @Column(name = "regid")
    var regId: String = ""

    @JsonIgnore
    @Column(name = "regdate")
    var redDate: LocalDateTime? = null

    @JsonIgnore
    @Column(name = "updid")
    var updId: String = ""

    @JsonIgnore
    @Column(name = "upddate")
    var updDate: LocalDateTime? = null

    @JsonIgnore
    @Column(name = "version")
    @Version
    var version: Int = 1

    @Transient
    @JsonIgnore
    var auditUser: String = ""

    @Transient
    @JsonIgnore
    var auditDateTime:LocalDateTime? = null

}