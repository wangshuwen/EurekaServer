package com.cst.xinhe.web.service.station_partition.service.impl;


import com.cst.xinhe.persistence.dao.t_partition.PartitionMapper;
import com.cst.xinhe.persistence.model.t_partition.Partition;
import com.cst.xinhe.persistence.model.t_partition.PartitionExample;
import com.cst.xinhe.stationpartition.service.service.BaseStationService;
import com.cst.xinhe.stationpartition.service.service.PartitionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangshuwen
 * @Description:
 * @Date 2019/1/11/14:02
 */
@Service
public class PartitionServiceImpl implements PartitionService {
    @Resource
    private PartitionMapper partitionMapper;
    @Resource
    private BaseStationService baseStationService;

    @Override
    public List<Partition> findPartition() {
        return findAllByParentId(0);
    }

    @Override
    public Integer add(Partition partition) {
        return partitionMapper.insert(partition);
    }

    @Override
    public Integer delete(Integer id) {
        return deleteAllByParentId(id);
    }

    @Override
    public String geParentNamesById(Integer id) {
        return findParentNamesById(id);
    }

    @Override
    public  List<Integer> getSonIdsById(Integer id) {
        List<Integer> sonIdsById = findSonIdsById(id);
        sonIdsById.add(id);
        return sonIdsById;
    }




    @Override
    public Integer update(Partition partition) {
        return partitionMapper.updateByPrimaryKeySelective(partition);
    }



    /**
     * 方法实现说明
     * @author      wangshuwen
     * @description  通过parentId查找该parentId所有的子节点
     * @date        2019/1/9 11:07
     */
    private List<Partition> findAllByParentId(Integer parentId){
        PartitionExample example = new PartitionExample();
        example.setOrderByClause("sort asc");
        example.createCriteria().andParentIdEqualTo(parentId);
        //查询parent_id为0的顶级节点
        List<Partition> list = partitionMapper.selectByExample(example);
        if(list!=null){
            for (Partition partition : list) {
                partition.setLabel(partition.getPartitionName());
                List<Partition> children = findAllByParentId(partition.getId());
                if(children!=null){
                    partition.setChildren(children);
                }
            }

        }

        return list;
    }

    /**
     * 方法实现说明
     * @author      wangshuwen
     * @description  递归删除所有的子节点通过parentId
     * @date        2019/1/9 11:07
     */
    private Integer deleteAllByParentId(Integer parentId){
        //
        int result=partitionMapper.deleteByPrimaryKey(parentId);
        PartitionExample example = new PartitionExample();
        example.createCriteria().andParentIdEqualTo(parentId);
        //查询parent_id为0的顶级节点
        List<Partition> list = partitionMapper.selectByExample(example);
        if(list!=null){
            for (Partition partition : list) {
                result+= deleteAllByParentId(partition.getId());
            }

        }

        return result;
    }


    /**
     * 方法实现说明
     * @author      wangshuwen
     * @description  通过id查找本节点所有的父节点,把父节点名称拼成串
     * @date        2019/1/9 11:07
     */
    private String findParentNamesById(Integer id){
        String partitionName="";
        Partition partition = partitionMapper.selectByPrimaryKey(id);
        if(partition!=null){
            partitionName=partition.getPartitionName();
            if(partition.getParentId()!=0){
                String parentName = findParentNamesById(partition.getParentId());
                partitionName=parentName+"/"+partitionName;
            }
        }

        return partitionName;
    }

    /**
     * @author      wangshuwen
     * @description  通过id查找本节点所有的子节点id(不包括本节点id)
     * @return
     * @date        2019/1/11 16:42
     */
    private  List<Integer> findSonIdsById(Integer id) {
        if(id==null){
            id=0;
        }
        ArrayList<Integer> list = new ArrayList<>();
        PartitionExample example = new PartitionExample();
        example.createCriteria().andParentIdEqualTo(id);
        List<Partition> sonList = partitionMapper.selectByExample(example);
        if(sonList!=null){
            for (Partition partition : sonList) {
                list.add(partition.getId());
                List<Integer> sonIds = findSonIdsById(partition.getId());
               if(sonIds!=null){
                   for (Integer sonId : sonIds) {
                       list.add(sonId);
                   }
               }
            }


        }
        return list;
    }

    public Integer findRootPartition(){
        PartitionExample partitionExample = new PartitionExample();
        PartitionExample.Criteria criteria = partitionExample.createCriteria();
        criteria.andParentIdEqualTo(0);
        List<Partition> partitionList = partitionMapper.selectByExample(partitionExample);
        if (partitionList.size() == 1){
            return partitionList.get(0).getId();
        }
        return 0;
    }

}
