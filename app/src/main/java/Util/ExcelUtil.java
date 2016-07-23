package Util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import bean.Item;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ExcelUtil {

    public static void itemExport(String filePath,String fileName,List<Item> list){
        try {
            String[] array = new String[]{"日期","时间","品牌","类型","型号","操作者","箱柜号","动作/去向"};
            List<String> titleList = new LinkedList<String>(Arrays.asList(array));
            File file = new File((filePath+"\\"+fileName));
            if (!file.isFile())
                file.createNewFile();
            OutputStream os = new FileOutputStream(file);
            WritableWorkbook book = Workbook.createWorkbook(os);
            WritableSheet sheet = book.createSheet("存取记录", 0);
            for (int j = 0; j < titleList.size(); j++){
                Label label = new Label(j,0,titleList.get(j));
                sheet.addCell(label);
            }
            for (int i = 0; i < list.size(); i++) {
                Item item = list.get(i);
                Label label0i = new Label(0,i+1,item.getTime());
                sheet.addCell(label0i);

            }

            book.write();
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

}
