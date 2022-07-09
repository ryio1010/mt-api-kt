package com.muscletracking.mtapi.entity

import org.seasar.doma.jdbc.entity.EntityListener
import org.seasar.doma.jdbc.entity.PreInsertContext
import org.seasar.doma.jdbc.entity.PreUpdateContext

class BaseColumnEntityListener<ENTITY>: EntityListener<ENTITY> {

    override fun preInsert(entity:ENTITY, context:PreInsertContext<ENTITY>) {
        if (entity is BaseColumnEntity) {
            val auditUser = entity.auditUser
            val auditDate = entity.auditDateTime

            entity.regId = auditUser
            entity.redDate = auditDate
            entity.updId = auditUser
            entity.updDate = auditDate
        }
    }

    override fun preUpdate(entity:ENTITY, context:PreUpdateContext<ENTITY>) {
        if (entity is BaseColumnEntity) {
            val auditUser = entity.auditUser
            val auditDate = entity.auditDateTime

            entity.updId = auditUser
            entity.updDate = auditDate
        }
    }
}