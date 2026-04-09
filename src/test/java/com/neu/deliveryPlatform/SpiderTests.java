package com.neu.deliveryPlatform;

import com.alibaba.fastjson2.JSON;
import com.neu.deliveryPlatform.dto.cmd.AddOrderCmd;

import lombok.Data;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpiderTests {
    void spider() throws IOException, ParseException {
        // 设置 ChromeDriver 路径
        System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");

        // 配置 ChromeOptions
        //无浏览器配置
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setHeadless(Boolean.TRUE);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");

        // 创建 ChromeDriver 实例
        WebDriver driver = new ChromeDriver(options);

        //无浏览器配置
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        Dimension dimension = new Dimension(1920, 1080);
        driver.manage().window().setSize(dimension);


        // 打开网页
        String url = "https://i.mall.fkw.com/?siteId=0&_openmf=manageFrameOrderManage";
        driver.get(url);

        // 登录
        String memberButtonPath = "/html/body/div[2]/div[2]/div[5]/div[4]/div[3]/div[4]/div/div[1]";
        String userPath = "/html/body/div[2]/div[2]/div[5]/div[4]/div[3]/div[1]/input";
        String memberUserPath = "/html/body/div[2]/div[2]/div[5]/div[4]/div[3]/div[2]/input";
        String passwordPath = "/html/body/div[2]/div[2]/div[5]/div[4]/div[3]/div[3]/input";
        String loginPath = "/html/body/div[2]/div[2]/div[5]/div[4]/div[7]";

        WebElement memberButton = driver.findElement(By.xpath(memberButtonPath));
        memberButton.click();

        WebElement user = driver.findElement(By.xpath(userPath));
        user.sendKeys("pp18976124");

        WebElement memberUser = driver.findElement(By.xpath(memberUserPath));
        memberUser.sendKeys("lcx");

        WebElement password = driver.findElement(By.xpath(passwordPath));
        password.sendKeys("lcx123");

        WebElement login = driver.findElement(By.xpath(loginPath));
        login.click();

        // 关闭广告
        String adsClosePath = "/html/body/div[8]/div/div[2]/div/div[2]/div/div/div";
        WebElement adsClose = (new WebDriverWait(driver,Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(adsClosePath)));
        adsClose.click();

        String adsClosePath2 = "/html/body/div[7]/div/div/div/div/div/i[2]";
        WebElement adsClose2 = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(adsClosePath2)));
        adsClose2.click();

        // 进入 iframe
        String iframePath = "/html/body/div[2]/div[2]/div[3]/div/div[2]/iframe";
        WebElement iframe = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(iframePath)));
        driver.switchTo().frame(iframe);

        // 选择同城配送
        String selectPath = "/html/body/div[1]/div[2]/div[1]/div[9]/div/div";
        WebElement select = driver.findElement(By.xpath(selectPath));
        select.click();

        String deliveryPath = "/html/body/div[3]/div/div/div/ul/li[4]";
        WebElement delivery = (new WebDriverWait(driver, Duration.ofSeconds(10)))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(deliveryPath)));
        delivery.click();

        // 获取一页中的所有数据
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String preCnt = getCntApi();
        String preDate=preCnt.substring(0,19);
        String preEndNumber=preCnt.substring(19);
        StringBuffer nextDate=new StringBuffer();
        StringBuffer nextEndNumber=new StringBuffer();


        BooleanClass firstData=new BooleanClass();
        firstData.setValue(false);
        while (true) {
            try {
                Thread.sleep(1000);
                if (!pageSpider(driver, preDate,preEndNumber,nextDate,nextEndNumber,firstData)) {
                    break;
                }
                WebElement nextPage = driver.findElement(By.className("jz-page-next"));
                String nextPageDisabled="jz-page-next jz-page-disabled";
                if(!nextPage.getAttribute("class").equals(nextPageDisabled)){
                    nextPage.click();
                }else{
                    break;
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
        updateCntApi(nextDate.toString(),nextEndNumber.toString());

        // 关闭浏览器
        driver.quit();
    }

    @Data
    private static class CommodityDetail{
        private String name;
        private String price;
    }

    private static class BooleanClass{
        private boolean value;
        public boolean getValue() {
            return value;
        }
        public void setValue(boolean value) {
            this.value = value;
        }
    }

    //爬取一页中的所有数据
    private static boolean pageSpider(WebDriver driver, String preDate,String preEndNumber,StringBuffer nextDate,StringBuffer nextEndNumber,BooleanClass firstData) throws IOException, ParseException {
        String orderListContentClassName = "orderListContent";
        String orderContentClassName = "orderContent";
        String orderHeadClassName = "orderHead";
        String commodityClassName = "proNameDetail";
        String priceClassName = "orderCol orderCol2";
        String buyerClassName = "orderCol orderCol4";
        String paidClassName = "orderCol orderCol5";
        String statusClassName = "orderCol orderCol6";
        String detailClassName = "viewIcon";
        String returnClassName = "return_area";
        String addressPath = "/html/body/div[2]/div/div[3]/div[1]/div[2]/div[2]/div/div[1]/div/div/div/div/span[2]/span[2]";
        String remarkPath =  "/html/body/div[2]/div/div[3]/div[1]/div[2]/div[2]/div/div[1]/div/div/div/div/span[5]/span[2]";

        // 获取 orderListContent 元素集合
        List<WebElement> orderListContent = driver.findElements(By.className(orderListContentClassName));
        for (WebElement x : orderListContent) {
            boolean flag = false;
            List<CommodityDetail> commoditys = new ArrayList<>();
            String buyer = "";
            String paid = "";
            String status = "";
            String address = "";
            String remark="";

            WebElement orderHead = x.findElement(By.className(orderHeadClassName));
            String texts = orderHead.getText();
            String id = extractId(texts);
            String orderTime = extractOrderTime(texts);

            WebElement detail = orderHead.findElement(By.className(detailClassName));
            detail.click();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(addressPath)));
            WebElement addressElement = driver.findElement(By.xpath(addressPath));
            address = addressElement.getText();
            WebElement remarkElement=driver.findElement(By.xpath(remarkPath));
            remark=remarkElement.getText();
            WebElement returnButton = driver.findElement(By.className(returnClassName));
            returnButton.click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className(orderContentClassName)));
            List<WebElement> orderContent = x.findElements(By.className(orderContentClassName));
            for (WebElement y : orderContent) {
                WebElement commodity = y.findElement(By.className(commodityClassName));
                WebElement price = y.findElement(By.cssSelector("[class='"+priceClassName+"']"));
                CommodityDetail commodityDetail=new CommodityDetail();
                commodityDetail.setName(commodity.getText());
                commodityDetail.setPrice(price.getText());
                commoditys.add(commodityDetail);
                if (!flag) {
                    WebElement buyerElement = y.findElement(By.cssSelector("[class='"+buyerClassName+"']"));
                    WebElement paidElement = y.findElement(By.cssSelector("[class='"+paidClassName+"']"));
                    WebElement statusElement = y.findElement(By.cssSelector("[class='"+statusClassName+"']"));
                    buyer = buyerElement.getText();
                    paid = paidElement.getText();
                    status = statusElement.getText();
                    flag = true;
                }
            }



            // 根据时间戳判断是否已经爬过
            if (orderTime.equals(preDate)&&id.substring(9).equals(preEndNumber)) {
                return false;
            }
            //增加订单
            addOrder(id,orderTime,buyer,paid,status,address,commoditys,remark);
            //更新时间戳
            if(firstData.getValue()==false){
                firstData.setValue(true);
                nextDate.append(orderTime);
                nextEndNumber.append(id.substring(9));
            }
        }
        return true;
    }

    //匹配订单id的模式串
    private static String extractId(String text) {
        Pattern pattern = Pattern.compile("\\d{13}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    //匹配订单时间的模式串
    private static String extractOrderTime(String text) {
        Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    //得到字符串的电话号部分
    private static String extractPhoneNumber(String text) {
        Pattern pattern = Pattern.compile("\\d{11}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    //得到字符串的小数部分
    private static String extractDoubleNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+\\.\\d+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    //得到字符串的数字部分
    private static String extractIntegerNumber(String text) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    //得到字符串的一行
    private static String getFirstLine(String text) {
        int newlineIndex = text.indexOf("\n");
        if (newlineIndex != -1) {
            return text.substring(0, newlineIndex);
        } else {
            return text;
        }
    }

    //得到字符串的二行
    private static String getSecondLine(String text) {
        int newlineIndex = text.indexOf("\n");
        if (newlineIndex != -1) {
            return text.substring(newlineIndex+1);
        } else {
            return text;
        }
    }

    //获取上次爬取的时间戳
    private static String getCntApi() throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        String url="http://localhost:8000/api/spider/getCnt";
        try {
            // 创建 URL 对象
            URL requestUrl = new URL(url);

            // 打开连接
            connection = (HttpURLConnection) requestUrl.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            //登陆的头部
            connection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmYmFkNzU1OWViYjM0ZTFkYmQyMzNhNGIzMGNmMTUwZiIsInN1YiI6IjkiLCJpc3MiOiJzZyIsImlhdCI6MTY4MTgwNDIzNywiZXhwIjoxNzEzMzQwMjM3fQ.uFTyb6HeCpUguOeoqZKsmBolTSLEFvjh68FnKexAQsg");

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // 关闭连接和读取器
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response.toString();
    }

    private static String updateCntApi(String date, String endNumber) throws IOException {
        date=URLEncoder.encode(date, "UTF-8");
        endNumber=URLEncoder.encode(endNumber, "UTF-8");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        String url="http://localhost:8000/api/spider/updateCnt?date="+date+"&endNumber="+endNumber;
        try {
            // 创建 URL 对象
            URL requestUrl = new URL(url);

            // 打开连接
            connection = (HttpURLConnection) requestUrl.openConnection();

            // 设置请求方法为 POST
            connection.setRequestMethod("POST");

            //登陆的头部
            connection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmYmFkNzU1OWViYjM0ZTFkYmQyMzNhNGIzMGNmMTUwZiIsInN1YiI6IjkiLCJpc3MiOiJzZyIsImlhdCI6MTY4MTgwNDIzNywiZXhwIjoxNzEzMzQwMjM3fQ.uFTyb6HeCpUguOeoqZKsmBolTSLEFvjh68FnKexAQsg");

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // 关闭连接和读取器
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response.toString();
    }

    //调用根据商品名和价格查询商品id得到接口
    private static String getCommodityIdByNameAndPriceApi(String name,String price) throws IOException {
        name= URLEncoder.encode(name, "UTF-8");
        price=URLEncoder.encode(price, "UTF-8");
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        String url="http://localhost:8000/api/commodity/getByNameAndPrice?keyword="+name+"&price="+price;
        try {
            // 创建 URL 对象
            URL requestUrl = new URL(url);

            // 打开连接
            connection = (HttpURLConnection) requestUrl.openConnection();

            // 设置请求方法为 GET
            connection.setRequestMethod("GET");

            //登陆的头部
            connection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmYmFkNzU1OWViYjM0ZTFkYmQyMzNhNGIzMGNmMTUwZiIsInN1YiI6IjkiLCJpc3MiOiJzZyIsImlhdCI6MTY4MTgwNDIzNywiZXhwIjoxNzEzMzQwMjM3fQ.uFTyb6HeCpUguOeoqZKsmBolTSLEFvjh68FnKexAQsg");

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // 关闭连接和读取器
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response.toString();
    }

    //调用添加订单的接口
    private static String addOrderApi(AddOrderCmd addOrderCmd) throws IOException {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuilder response = new StringBuilder();
        String url="http://localhost:8000/api/order/add";
        try {
            // 创建 URL 对象
            URL requestUrl = new URL(url);

            // 打开连接
            connection = (HttpURLConnection) requestUrl.openConnection();

            // 设置请求方法为 POST
            connection.setRequestMethod("POST");

            // 启用输出流
            connection.setDoOutput(true);

            //登陆的头部
            connection.setRequestProperty("Authorization", "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmYmFkNzU1OWViYjM0ZTFkYmQyMzNhNGIzMGNmMTUwZiIsInN1YiI6IjkiLCJpc3MiOiJzZyIsImlhdCI6MTY4MTgwNDIzNywiZXhwIjoxNzEzMzQwMjM3fQ.uFTyb6HeCpUguOeoqZKsmBolTSLEFvjh68FnKexAQsg");
            //数据类型
            connection.setRequestProperty("Content-Type", "application/json");


            String requestBody = JSON.toJSONString(addOrderCmd);

            OutputStream outputStream = connection.getOutputStream();

            // 写入请求体数据
            outputStream.write(requestBody.getBytes());

            // 关闭输出流
            outputStream.close();

            // 读取响应
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } finally {
            // 关闭连接和读取器
            if (reader != null) {
                reader.close();
            }
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response.toString();
    }

    //添加订单
    private static void addOrder(String id, String orderTime, String buyer, String paid, String status, String address, List<CommodityDetail> commoditys, String remark) throws IOException {
        AddOrderCmd addOrderCmd=new AddOrderCmd();
        addOrderCmd.setPreferentialPrice(0.0);
        List<Long>shopkeeperIds=new ArrayList<>();
        shopkeeperIds.add(new Long(1));
        addOrderCmd.setShopkeeperIds(shopkeeperIds);
        addOrderCmd.setStartingPointAddress("东秦印象门店");
        addOrderCmd.setType(2);
        addOrderCmd.setUserId(new Long(9));


        List<Long>commodityIds=new ArrayList<>();
        for(CommodityDetail commodityDetail:commoditys){
            String name=commodityDetail.getName();
            String price=commodityDetail.getPrice();
            String priceForOne=getFirstLine(price);
            priceForOne=extractDoubleNumber(priceForOne);
            String nums=getSecondLine(price);
            nums=extractIntegerNumber(nums);
            for(int i=0;i<Integer.parseInt(nums);i++){
                String str=getCommodityIdByNameAndPriceApi(getFirstLine(name),priceForOne);
                str = str.substring(1, str.length() - 1);
                Long commodityId=Long.parseLong(str);
                commodityIds.add(commodityId);
            }
        }
        addOrderCmd.setCommodityIds(commodityIds);

        addOrderCmd.setEndNumber(Integer.parseInt(id.substring(9)));
        addOrderCmd.setPrice(Double.parseDouble(extractDoubleNumber(paid)));
        addOrderCmd.setRemark(remark);
        addOrderCmd.setTerminalAddress(address);
        addOrderCmd.setUserPhoneNumber(extractPhoneNumber(buyer));


        addOrderApi(addOrderCmd);
    }

}
