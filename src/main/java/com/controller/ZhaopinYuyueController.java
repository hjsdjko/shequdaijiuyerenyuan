
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 预约面试
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/zhaopinYuyue")
public class ZhaopinYuyueController {
    private static final Logger logger = LoggerFactory.getLogger(ZhaopinYuyueController.class);

    private static final String TABLE_NAME = "zhaopinYuyue";

    @Autowired
    private ZhaopinYuyueService zhaopinYuyueService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表非注册的service
    @Autowired
    private ZhaopinService zhaopinService;
    @Autowired
    private JianliService jianliService;
    //注册表service
    @Autowired
    private YonghuService yonghuService;
    @Autowired
    private GongzuorenyuanService gongzuorenyuanService;


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("用户".equals(role))
            params.put("jianliYonghuId",request.getSession().getAttribute("userId"));
        else if("社区工作人员".equals(role))
            params.put("gongzuorenyuanId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = zhaopinYuyueService.queryPage(params);

        //字典表数据转换
        List<ZhaopinYuyueView> list =(List<ZhaopinYuyueView>)page.getList();
        for(ZhaopinYuyueView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ZhaopinYuyueEntity zhaopinYuyue = zhaopinYuyueService.selectById(id);
        if(zhaopinYuyue !=null){
            //entity转view
            ZhaopinYuyueView view = new ZhaopinYuyueView();
            BeanUtils.copyProperties( zhaopinYuyue , view );//把实体数据重构到view中
            //级联表 工作岗位
            //级联表
            ZhaopinEntity zhaopin = zhaopinService.selectById(zhaopinYuyue.getZhaopinId());
            if(zhaopin != null){
            BeanUtils.copyProperties( zhaopin , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setZhaopinId(zhaopin.getId());
            }
            //级联表 就业信息
            //级联表
            JianliEntity jianli = jianliService.selectById(zhaopinYuyue.getJianliId());
            if(jianli != null){
            BeanUtils.copyProperties( jianli , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setJianliId(jianli.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody ZhaopinYuyueEntity zhaopinYuyue, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,zhaopinYuyue:{}",this.getClass().getName(),zhaopinYuyue.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<ZhaopinYuyueEntity> queryWrapper = new EntityWrapper<ZhaopinYuyueEntity>()
            .eq("jianli_id", zhaopinYuyue.getJianliId())
            .eq("zhaopin_id", zhaopinYuyue.getZhaopinId())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhaopinYuyueEntity zhaopinYuyueEntity = zhaopinYuyueService.selectOne(queryWrapper);
        if(zhaopinYuyueEntity==null){
            zhaopinYuyue.setZhaopinYuyueYesnoTypes(1);
            zhaopinYuyue.setInsertTime(new Date());
            zhaopinYuyue.setCreateTime(new Date());
            zhaopinYuyueService.insert(zhaopinYuyue);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ZhaopinYuyueEntity zhaopinYuyue, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,zhaopinYuyue:{}",this.getClass().getName(),zhaopinYuyue.toString());
        ZhaopinYuyueEntity oldZhaopinYuyueEntity = zhaopinYuyueService.selectById(zhaopinYuyue.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<ZhaopinYuyueEntity> queryWrapper = new EntityWrapper<ZhaopinYuyueEntity>()
            .notIn("id",zhaopinYuyue.getId())
            .andNew()
            .eq("jianli_id", zhaopinYuyue.getJianliId())
            .eq("zhaopin_id", zhaopinYuyue.getZhaopinId())
            .eq("zhaopin_yuyue_time", zhaopinYuyue.getZhaopinYuyueTime())
            .eq("insert_time", zhaopinYuyue.getInsertTime())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhaopinYuyueEntity zhaopinYuyueEntity = zhaopinYuyueService.selectOne(queryWrapper);
        if(zhaopinYuyueEntity==null){
            zhaopinYuyueService.updateById(zhaopinYuyue);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 审核
    */
    @RequestMapping("/shenhe")
    public R shenhe(@RequestBody ZhaopinYuyueEntity zhaopinYuyueEntity, HttpServletRequest request){
        logger.debug("shenhe方法:,,Controller:{},,zhaopinYuyueEntity:{}",this.getClass().getName(),zhaopinYuyueEntity.toString());

        ZhaopinYuyueEntity oldZhaopinYuyue = zhaopinYuyueService.selectById(zhaopinYuyueEntity.getId());//查询原先数据

//        if(zhaopinYuyueEntity.getZhaopinYuyueYesnoTypes() == 2){//通过
//            zhaopinYuyueEntity.setZhaopinYuyueTypes();
//        }else if(zhaopinYuyueEntity.getZhaopinYuyueYesnoTypes() == 3){//拒绝
//            zhaopinYuyueEntity.setZhaopinYuyueTypes();
//        }
        zhaopinYuyueService.updateById(zhaopinYuyueEntity);//审核

        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<ZhaopinYuyueEntity> oldZhaopinYuyueList =zhaopinYuyueService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        zhaopinYuyueService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            List<ZhaopinYuyueEntity> zhaopinYuyueList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            ZhaopinYuyueEntity zhaopinYuyueEntity = new ZhaopinYuyueEntity();
//                            zhaopinYuyueEntity.setJianliId(Integer.valueOf(data.get(0)));   //就业信息 要改的
//                            zhaopinYuyueEntity.setZhaopinId(Integer.valueOf(data.get(0)));   //招聘 要改的
//                            zhaopinYuyueEntity.setZhaopinYuyueTime(sdf.parse(data.get(0)));          //预约时间 要改的
//                            zhaopinYuyueEntity.setZhaopinYuyueYesnoTypes(Integer.valueOf(data.get(0)));   //审核结果 要改的
//                            zhaopinYuyueEntity.setZhaopinYuyueYesnoText(data.get(0));                    //审核原因 要改的
//                            zhaopinYuyueEntity.setInsertTime(date);//时间
//                            zhaopinYuyueEntity.setCreateTime(date);//时间
                            zhaopinYuyueList.add(zhaopinYuyueEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        zhaopinYuyueService.insertBatch(zhaopinYuyueList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }





    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = zhaopinYuyueService.queryPage(params);

        //字典表数据转换
        List<ZhaopinYuyueView> list =(List<ZhaopinYuyueView>)page.getList();
        for(ZhaopinYuyueView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ZhaopinYuyueEntity zhaopinYuyue = zhaopinYuyueService.selectById(id);
            if(zhaopinYuyue !=null){


                //entity转view
                ZhaopinYuyueView view = new ZhaopinYuyueView();
                BeanUtils.copyProperties( zhaopinYuyue , view );//把实体数据重构到view中

                //级联表
                    ZhaopinEntity zhaopin = zhaopinService.selectById(zhaopinYuyue.getZhaopinId());
                if(zhaopin != null){
                    BeanUtils.copyProperties( zhaopin , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setZhaopinId(zhaopin.getId());
                }
                //级联表
                    JianliEntity jianli = jianliService.selectById(zhaopinYuyue.getJianliId());
                if(jianli != null){
                    BeanUtils.copyProperties( jianli , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setJianliId(jianli.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody ZhaopinYuyueEntity zhaopinYuyue, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,zhaopinYuyue:{}",this.getClass().getName(),zhaopinYuyue.toString());
        Wrapper<ZhaopinYuyueEntity> queryWrapper = new EntityWrapper<ZhaopinYuyueEntity>()
            .eq("jianli_id", zhaopinYuyue.getJianliId())
            .eq("zhaopin_id", zhaopinYuyue.getZhaopinId())
            .eq("zhaopin_yuyue_yesno_types", zhaopinYuyue.getZhaopinYuyueYesnoTypes())
            .eq("zhaopin_yuyue_yesno_text", zhaopinYuyue.getZhaopinYuyueYesnoText())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ZhaopinYuyueEntity zhaopinYuyueEntity = zhaopinYuyueService.selectOne(queryWrapper);
        if(zhaopinYuyueEntity==null){
            zhaopinYuyue.setZhaopinYuyueYesnoTypes(1);
            zhaopinYuyue.setInsertTime(new Date());
            zhaopinYuyue.setCreateTime(new Date());
        zhaopinYuyueService.insert(zhaopinYuyue);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}
