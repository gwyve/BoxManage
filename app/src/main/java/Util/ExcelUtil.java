package Util;

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import bean.Box;
import bean.Item;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ExcelUtil {

    public static void itemExport(String filePath,String fileName,List<Item> list){
        try {
            String[] tileArray = new String[]{"日期","时间","品牌","型号","类型","备注","操作者","箱柜号","动作/去向"};
            File file = new File((filePath+"/"+fileName));
            if (file.exists())
                file.delete();
            file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            WritableWorkbook book = Workbook.createWorkbook(os);
            WritableSheet sheet = book.createSheet("存取记录", 0);
            for (int j = 0; j < tileArray.length; j++){
                Label label = new Label(j,0,tileArray[j]);
                sheet.addCell(label);
            }
            for (int i = 1; i <= list.size(); i++) {
                Item item = list.get(i-1);
                String[] times = item.getTime().split("_");
                sheet.addCell(new Label(0,i,times[0]));
                sheet.addCell(new Label(1,i,times[1]));
                sheet.addCell(new Label(2,i,item.getVendor()));
                sheet.addCell(new Label(3,i,item.getModel()));
                sheet.addCell(new Label(4,i,item.getType()));
                sheet.addCell(new Label(5,i,item.getMemo()));
                sheet.addCell(new Label(6,i,item.getPersonName()));
                sheet.addCell(new Label(7,i,item.getBox()));
                if (item.getAction().equals("putin")){
                    sheet.addCell(new Label(8,i,"放物"));
                }else {
                    sheet.addCell(new Label(8,i,item.getExplain()));
                }
            }

            book.write();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }


    public static void boxExport(String filePath,String fileName,List<Box> list){
        try {
            String[] tileArray = new String[]{"箱柜号","品牌","型号","类型","备注","数量"};
            File file = new File((filePath+"/"+fileName));
            if (file.exists())
                file.delete();
            file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            WritableWorkbook book = Workbook.createWorkbook(os);
            WritableSheet sheet = book.createSheet("存取记录", 0);
            for (int j = 0; j < tileArray.length; j++){
                Label label = new Label(j,0,tileArray[j]);
                sheet.addCell(label);
            }
            for (int i=1; i <= list.size();i++ ){
                Box box = list.get(i-1);
                sheet.addCell(new Label(0,i,box.getBox()));
                sheet.addCell(new Label(1,i,box.getVendor()));
                sheet.addCell(new Label(2,i,box.getModel()));
                sheet.addCell(new Label(3,i,box.getType()));
                sheet.addCell(new Label(4,i,box.getMemo()));
                sheet.addCell(new Label(5,i,String.valueOf(box.getNumber())));
            }
            book.write();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RowsExceededException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

}
