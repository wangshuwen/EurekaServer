package com.cst.xinhe.staffgroupterminal.service.service;


import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;

public interface StaffTerminalRelationService {

    StaffTerminalRelation insert(StaffTerminalRelation staffTerminalRelation);

    StaffTerminalRelation findNewRelationByStaffId(Integer staffId);

    StaffTerminalRelation findNewRelationByTerminalId(Integer terminalId);

    StaffTerminalRelation findHistoryRelationById(Integer staffTerminalRelationId);

    int updateRelationToOld(StaffTerminalRelation staffTerminalRelation);
}
