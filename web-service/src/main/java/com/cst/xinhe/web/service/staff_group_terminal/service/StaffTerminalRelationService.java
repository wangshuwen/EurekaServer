package com.cst.xinhe.web.service.staff_group_terminal.service;


import com.cst.xinhe.persistence.model.staff_terminal_relation.StaffTerminalRelation;

public interface StaffTerminalRelationService {

    StaffTerminalRelation insert(StaffTerminalRelation staffTerminalRelation);

    StaffTerminalRelation findNewRelationByStaffId(Integer staffId);

    StaffTerminalRelation findNewRelationByTerminalId(Integer terminalId);

    StaffTerminalRelation findHistoryRelationById(Integer staffTerminalRelationId);

    int updateRelationToOld(StaffTerminalRelation staffTerminalRelation);
}
