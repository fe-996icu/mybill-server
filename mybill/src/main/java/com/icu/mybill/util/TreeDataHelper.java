package com.icu.mybill.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;

import java.util.List;
import java.util.Map;

/**
 * 树形结构数据工具类
 */
public class TreeDataHelper {

    /**
     * 将实体列表转换为树形结构，默认递归深度2层
     *
     * @param entities 实体列表
     * @param <T>      实体类型
     * @return 树形结构列表
     */
    public static <T> List<Tree<Long>> beanListToTreeList(List<T> entities) {
        return beanListToTreeList(entities, false);
    }

    /**
     * 将实体列表转换为树形结构，默认递归深度2层
     *
     * @param entities              实体列表
     * @param ensureChildrenNotNull 如果子节点 children 为空，则设置为空数组
     * @param <T>                   实体类型
     * @return 树形结构列表
     */
    public static <T> List<Tree<Long>> beanListToTreeList(List<T> entities, boolean ensureChildrenNotNull) {
        return beanListToTreeList(entities, "id", "parentId", "sort", 2, ensureChildrenNotNull);
    }

    /**
     * 将实体列表转换为树形结构
     *
     * @param entities              实体列表
     * @param idKey                 ID 字段名
     * @param parentIdKey           父 ID 字段名
     * @param sortKey               排序字段名
     * @param maxDepth              树的最大深度（可选）
     * @param ensureChildrenNotNull 如果子节点 children 为空，则设置为空数组
     * @param <T>                   实体类型
     * @return 树形结构列表
     */
    public static <T> List<Tree<Long>> beanListToTreeList(List<T> entities, String idKey, String parentIdKey, String sortKey, Integer maxDepth, boolean ensureChildrenNotNull) {
        List<Tree<Long>> treeList = beanListToTreeList(entities, idKey, parentIdKey, sortKey, maxDepth);
        if(ensureChildrenNotNull){
            ensureChildrenNotNull(treeList);
        }
        return treeList;
    }

    /**
     * 将实体列表转换为树形结构
     *
     * @param entities      实体列表
     * @param idKey         ID 字段名
     * @param parentIdKey   父 ID 字段名
     * @param sortKey       排序字段名
     * @param maxDepth      树的最大深度（可选）
     * @param <T>           实体类型
     * @return              树形结构列表
     */
    public static <T> List<Tree<Long>> beanListToTreeList(List<T> entities, String idKey, String parentIdKey, String sortKey, Integer maxDepth) {
        // 配置树形结构
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey(idKey); // 设置 ID 字段名
        treeNodeConfig.setParentIdKey(parentIdKey); // 设置父 ID 字段名
        treeNodeConfig.setWeightKey(sortKey); // 设置排序字段名
        treeNodeConfig.setDeep(maxDepth); // 设置树的最大深度（可选）

        // 转换为树形结构
        return TreeUtil.build(entities, null, treeNodeConfig, (entity, tree) -> {
            Map<String, Object> entityMap = BeanUtil.beanToMap(entity);

            tree.setId((Long) entityMap.get(idKey));
            tree.setParentId((Long) entityMap.get(parentIdKey));
            // tree.setName((String) entityMap.get("name"));

            entityMap.forEach(tree::putExtra);
        });
    }

    /**
     * 确保每个节点的子节点不为空，如果为空则设置为空数组
     *
     * @param tree  树形结构列表
     */
    private static void ensureChildrenNotNull(List<Tree<Long>> tree) {
        if (tree != null) {
            for (Tree<Long> node : tree) {
                if (node.getChildren() == null) {
                    node.setChildren(List.of()); // 设置为空数组
                }
                ensureChildrenNotNull(node.getChildren()); // 递归子节点
            }
        }
    }
}
