
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
 * 工作推送信息
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/gongzuotuisong")
public class GongzuotuisongController {
    private static final Logger logger = LoggerFactory.getLogger(GongzuotuisongController.class);

    private static final String TABLE_NAME = "gongzuotuisong";

    @Autowired
    private GongzuotuisongService gongzuotuisongService;


    @Autowired
    private TokenService tokenService;
    @Autowired
    private DictionaryService dictionaryService;

    //级联表非注册的service
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
        params.put("gongzuotuisongDeleteStart",1);params.put("gongzuotuisongDeleteEnd",1);
        CommonUtil.checkMap(params);
        PageUtils page = gongzuotuisongService.queryPage(params);

        //字典表数据转换
        List<GongzuotuisongView> list =(List<GongzuotuisongView>)page.getList();
        for(GongzuotuisongView c:list){
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
        GongzuotuisongEntity gongzuotuisong = gongzuotuisongService.selectById(id);
        if(gongzuotuisong !=null){
            //entity转view
            GongzuotuisongView view = new GongzuotuisongView();
            BeanUtils.copyProperties( gongzuotuisong , view );//把实体数据重构到view中
            //级联表 社区工作人员
            //级联表
            GongzuorenyuanEntity gongzuorenyuan = gongzuorenyuanService.selectById(gongzuotuisong.getGongzuorenyuanId());
            if(gongzuorenyuan != null){
            BeanUtils.copyProperties( gongzuorenyuan , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "gongzuorenyuanId"
, "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setGongzuorenyuanId(gongzuorenyuan.getId());
            }
            //级联表 用户
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(gongzuotuisong.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "gongzuorenyuanId"
, "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
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
    public R save(@RequestBody GongzuotuisongEntity gongzuotuisong, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,gongzuotuisong:{}",this.getClass().getName(),gongzuotuisong.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("社区工作人员".equals(role))
            gongzuotuisong.setGongzuorenyuanId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        else if("用户".equals(role))
            gongzuotuisong.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<GongzuotuisongEntity> queryWrapper = new EntityWrapper<GongzuotuisongEntity>()
            .eq("gongzuorenyuan_id", gongzuotuisong.getGongzuorenyuanId())
            .eq("yonghu_id", gongzuotuisong.getYonghuId())
            .eq("gongzuotuisong_name", gongzuotuisong.getGongzuotuisongName())
            .eq("gongzuotuisong_types", gongzuotuisong.getGongzuotuisongTypes())
            .eq("gongzuotuisong_delete", gongzuotuisong.getGongzuotuisongDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        GongzuotuisongEntity gongzuotuisongEntity = gongzuotuisongService.selectOne(queryWrapper);
        if(gongzuotuisongEntity==null){
            gongzuotuisong.setInsertTime(new Date());
            gongzuotuisong.setGongzuotuisongDelete(1);
            gongzuotuisong.setCreateTime(new Date());
            gongzuotuisongService.insert(gongzuotuisong);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody GongzuotuisongEntity gongzuotuisong, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,gongzuotuisong:{}",this.getClass().getName(),gongzuotuisong.toString());
        GongzuotuisongEntity oldGongzuotuisongEntity = gongzuotuisongService.selectById(gongzuotuisong.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("社区工作人员".equals(role))
//            gongzuotuisong.setGongzuorenyuanId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
//        else if("用户".equals(role))
//            gongzuotuisong.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));
        //根据字段查询是否有相同数据
        Wrapper<GongzuotuisongEntity> queryWrapper = new EntityWrapper<GongzuotuisongEntity>()
            .notIn("id",gongzuotuisong.getId())
            .andNew()
            .eq("gongzuorenyuan_id", gongzuotuisong.getGongzuorenyuanId())
            .eq("yonghu_id", gongzuotuisong.getYonghuId())
            .eq("gongzuotuisong_name", gongzuotuisong.getGongzuotuisongName())
            .eq("gongzuotuisong_types", gongzuotuisong.getGongzuotuisongTypes())
            .eq("gongzuotuisong_delete", gongzuotuisong.getGongzuotuisongDelete())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        GongzuotuisongEntity gongzuotuisongEntity = gongzuotuisongService.selectOne(queryWrapper);
        if(gongzuotuisongEntity==null){
            gongzuotuisongService.updateById(gongzuotuisong);//根据id更新
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<GongzuotuisongEntity> oldGongzuotuisongList =gongzuotuisongService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        ArrayList<GongzuotuisongEntity> list = new ArrayList<>();
        for(Integer id:ids){
            GongzuotuisongEntity gongzuotuisongEntity = new GongzuotuisongEntity();
            gongzuotuisongEntity.setId(id);
            gongzuotuisongEntity.setGongzuotuisongDelete(2);
            list.add(gongzuotuisongEntity);
        }
        if(list != null && list.size() >0){
            gongzuotuisongService.updateBatchById(list);
        }

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
            List<GongzuotuisongEntity> gongzuotuisongList = new ArrayList<>();//上传的东西
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
                            GongzuotuisongEntity gongzuotuisongEntity = new GongzuotuisongEntity();
//                            gongzuotuisongEntity.setGongzuorenyuanId(Integer.valueOf(data.get(0)));   //工作人员 要改的
//                            gongzuotuisongEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            gongzuotuisongEntity.setGongzuotuisongName(data.get(0));                    //工作名称 要改的
//                            gongzuotuisongEntity.setGongzuotuisongTypes(Integer.valueOf(data.get(0)));   //工作类型 要改的
//                            gongzuotuisongEntity.setInsertTime(date);//时间
//                            gongzuotuisongEntity.setGongzuotuisongContent("");//详情和图片
//                            gongzuotuisongEntity.setGongzuotuisongDelete(1);//逻辑删除字段
//                            gongzuotuisongEntity.setCreateTime(date);//时间
                            gongzuotuisongList.add(gongzuotuisongEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        gongzuotuisongService.insertBatch(gongzuotuisongList);
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
        PageUtils page = gongzuotuisongService.queryPage(params);

        //字典表数据转换
        List<GongzuotuisongView> list =(List<GongzuotuisongView>)page.getList();
        for(GongzuotuisongView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        GongzuotuisongEntity gongzuotuisong = gongzuotuisongService.selectById(id);
            if(gongzuotuisong !=null){


                //entity转view
                GongzuotuisongView view = new GongzuotuisongView();
                BeanUtils.copyProperties( gongzuotuisong , view );//把实体数据重构到view中

                //级联表
                    GongzuorenyuanEntity gongzuorenyuan = gongzuorenyuanService.selectById(gongzuotuisong.getGongzuorenyuanId());
                if(gongzuorenyuan != null){
                    BeanUtils.copyProperties( gongzuorenyuan , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setGongzuorenyuanId(gongzuorenyuan.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(gongzuotuisong.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
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
    public R add(@RequestBody GongzuotuisongEntity gongzuotuisong, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,gongzuotuisong:{}",this.getClass().getName(),gongzuotuisong.toString());
        Wrapper<GongzuotuisongEntity> queryWrapper = new EntityWrapper<GongzuotuisongEntity>()
            .eq("gongzuorenyuan_id", gongzuotuisong.getGongzuorenyuanId())
            .eq("yonghu_id", gongzuotuisong.getYonghuId())
            .eq("gongzuotuisong_name", gongzuotuisong.getGongzuotuisongName())
            .eq("gongzuotuisong_types", gongzuotuisong.getGongzuotuisongTypes())
            .eq("gongzuotuisong_delete", gongzuotuisong.getGongzuotuisongDelete())
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        GongzuotuisongEntity gongzuotuisongEntity = gongzuotuisongService.selectOne(queryWrapper);
        if(gongzuotuisongEntity==null){
            gongzuotuisong.setInsertTime(new Date());
            gongzuotuisong.setGongzuotuisongDelete(1);
            gongzuotuisong.setCreateTime(new Date());
        gongzuotuisongService.insert(gongzuotuisong);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}
