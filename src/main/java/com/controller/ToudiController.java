
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
@RequestMapping("/toudi")
public class ToudiController {
    private static final Logger logger = LoggerFactory.getLogger(ToudiController.class);

    private static final String TABLE_NAME = "toudi";

    @Autowired
    private ToudiService toudiService;


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
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("社区工作人员".equals(role))
            params.put("gongzuorenyuanId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = toudiService.queryPage(params);

        //字典表数据转换
        List<ToudiView> list =(List<ToudiView>)page.getList();
        for(ToudiView c:list){
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
        ToudiEntity toudi = toudiService.selectById(id);
        if(toudi !=null){
            //entity转view
            ToudiView view = new ToudiView();
            BeanUtils.copyProperties( toudi , view );//把实体数据重构到view中
            //级联表 工作岗位
            //级联表
            ZhaopinEntity zhaopin = zhaopinService.selectById(toudi.getZhaopinId());
            if(zhaopin != null){
            BeanUtils.copyProperties( zhaopin , view ,new String[]{ "id", "createTime", "insertTime", "updateTime"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setZhaopinId(zhaopin.getId());
            }
            //级联表 就业信息
            //级联表
            JianliEntity jianli = jianliService.selectById(toudi.getJianliId());
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
    public R save(@RequestBody ToudiEntity toudi, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,toudi:{}",this.getClass().getName(),toudi.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<ToudiEntity> queryWrapper = new EntityWrapper<ToudiEntity>()
            .eq("jianli_id", toudi.getJianliId())
            .eq("zhaopin_id", toudi.getZhaopinId())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ToudiEntity toudiEntity = toudiService.selectOne(queryWrapper);
        if(toudiEntity==null){
            toudi.setToudiYesnoTypes(1);
            toudi.setInsertTime(new Date());
            toudi.setCreateTime(new Date());
            toudiService.insert(toudi);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody ToudiEntity toudi, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,toudi:{}",this.getClass().getName(),toudi.toString());
        ToudiEntity oldToudiEntity = toudiService.selectById(toudi.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        //根据字段查询是否有相同数据
        Wrapper<ToudiEntity> queryWrapper = new EntityWrapper<ToudiEntity>()
            .notIn("id",toudi.getId())
            .andNew()
            .eq("jianli_id", toudi.getJianliId())
            .eq("zhaopin_id", toudi.getZhaopinId())
            .eq("toudi_time", toudi.getToudiTime())
            .eq("insert_time", toudi.getInsertTime())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ToudiEntity toudiEntity = toudiService.selectOne(queryWrapper);
        if(toudiEntity==null){
            toudiService.updateById(toudi);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }


    /**
    * 审核
    */
    @RequestMapping("/shenhe")
    public R shenhe(@RequestBody ToudiEntity toudiEntity, HttpServletRequest request){
        logger.debug("shenhe方法:,,Controller:{},,toudiEntity:{}",this.getClass().getName(),toudiEntity.toString());

        ToudiEntity oldToudi = toudiService.selectById(toudiEntity.getId());//查询原先数据

//        if(toudiEntity.getToudiYesnoTypes() == 2){//通过
//            toudiEntity.setToudiTypes();
//        }else if(toudiEntity.getToudiYesnoTypes() == 3){//拒绝
//            toudiEntity.setToudiTypes();
//        }
        toudiService.updateById(toudiEntity);//审核

        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<ToudiEntity> oldToudiList =toudiService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        toudiService.deleteBatchIds(Arrays.asList(ids));

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
            List<ToudiEntity> toudiList = new ArrayList<>();//上传的东西
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
                            ToudiEntity toudiEntity = new ToudiEntity();
//                            toudiEntity.setJianliId(Integer.valueOf(data.get(0)));   //就业信息 要改的
//                            toudiEntity.setZhaopinId(Integer.valueOf(data.get(0)));   //招聘 要改的
//                            toudiEntity.setToudiTime(sdf.parse(data.get(0)));          //预约时间 要改的
//                            toudiEntity.setToudiYesnoTypes(Integer.valueOf(data.get(0)));   //审核结果 要改的
//                            toudiEntity.setToudiYesnoText(data.get(0));                    //审核原因 要改的
//                            toudiEntity.setInsertTime(date);//时间
//                            toudiEntity.setCreateTime(date);//时间
                            toudiList.add(toudiEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        toudiService.insertBatch(toudiList);
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
        PageUtils page = toudiService.queryPage(params);

        //字典表数据转换
        List<ToudiView> list =(List<ToudiView>)page.getList();
        for(ToudiView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        ToudiEntity toudi = toudiService.selectById(id);
            if(toudi !=null){


                //entity转view
                ToudiView view = new ToudiView();
                BeanUtils.copyProperties( toudi , view );//把实体数据重构到view中

                //级联表
                    ZhaopinEntity zhaopin = zhaopinService.selectById(toudi.getZhaopinId());
                if(zhaopin != null){
                    BeanUtils.copyProperties( zhaopin , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setZhaopinId(zhaopin.getId());
                }
                //级联表
                    JianliEntity jianli = jianliService.selectById(toudi.getJianliId());
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
    public R add(@RequestBody ToudiEntity toudi, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,toudi:{}",this.getClass().getName(),toudi.toString());
        Wrapper<ToudiEntity> queryWrapper = new EntityWrapper<ToudiEntity>()
            .eq("jianli_id", toudi.getJianliId())
            .eq("zhaopin_id", toudi.getZhaopinId())
            .eq("toudi_yesno_types", toudi.getToudiYesnoTypes())
            .eq("toudi_yesno_text", toudi.getToudiYesnoText())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        ToudiEntity toudiEntity = toudiService.selectOne(queryWrapper);
        if(toudiEntity==null){
            toudi.setToudiYesnoTypes(1);
            toudi.setInsertTime(new Date());
            toudi.setCreateTime(new Date());
        toudiService.insert(toudi);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}
