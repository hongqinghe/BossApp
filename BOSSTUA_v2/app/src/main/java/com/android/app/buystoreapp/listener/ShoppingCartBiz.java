package com.android.app.buystoreapp.listener;

import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.app.buystoreapp.R;
import com.android.app.buystoreapp.bean.GroupGoods;
import com.android.app.buystoreapp.bean.ShoppingCarBean;
import com.android.app.buystoreapp.dao.ShoppingCartDao;
import com.android.app.utils.DecimalUtil;

import java.util.List;


public class ShoppingCartBiz {

    /**
     * 选择全部，点下全部按钮，改变所有商品选中状态
     */
    public static boolean selectAll(List<ShoppingCarBean> list, boolean isSelectAll, ImageView ivCheck) {
        isSelectAll = !isSelectAll;
        ShoppingCartBiz.checkItem(isSelectAll, ivCheck);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setIsGroupSelected(isSelectAll);
            for (int j = 0; j < list.get(i).getQueryCar().size(); j++) {
                if(list.get(i).getQueryCar().get(j).getProStatus()==1) {
                    list.get(i).getQueryCar().get(j).setIsChildSelected(isSelectAll);
                }else {
                    list.get(i).getQueryCar().get(j).setIsChildSelected(false);
                }
            }
        }
        return isSelectAll;
    }

    /**
     * 族内的所有组，是否都被选中，即全选
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllGroup(List<ShoppingCarBean> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isGroupSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 组内所有子选项是否全部被选中
     *
     * @param list
     * @return
     */
    private static boolean isSelectAllChild(List<GroupGoods> list) {
        for (int i = 0; i < list.size(); i++) {
            boolean isSelectGroup = list.get(i).isChildSelected();
            if (!isSelectGroup) {
                return false;
            }
        }
        return true;
    }

    /**
     * 单选一个，需要判断整个组的标志，整个族的标志，是否被全选，取消，则
     * 除了选择全部和选择单个可以单独设置背景色，其他都是通过改变值，然后notify；
     *
     * @param list
     * @param groudPosition
     * @param childPosition
     * @return 是否选择全部
     */
    public static boolean selectOne(List<ShoppingCarBean> list, int groudPosition, int childPosition) {
        boolean isSelectAll;
        boolean isSelectedOne = !(list.get(groudPosition).getQueryCar().get(childPosition).isChildSelected());
        list.get(groudPosition).getQueryCar().get(childPosition).setIsChildSelected(isSelectedOne);//单个图标的处理
        boolean isSelectCurrentGroup = isSelectAllChild(list.get(groudPosition).getQueryCar());
        list.get(groudPosition).setIsGroupSelected(isSelectCurrentGroup);//组图标的处理
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    public static boolean selectGroup(List<ShoppingCarBean> list, int groudPosition) {
        boolean isSelectAll;
        boolean isSelected = !(list.get(groudPosition).isGroupSelected());
        list.get(groudPosition).setIsGroupSelected(isSelected);
        for (int i = 0; i < list.get(groudPosition).getQueryCar().size(); i++) {
            if(list.get(groudPosition).getQueryCar().get(i).getProStatus()==1) {
                list.get(groudPosition).getQueryCar().get(i).setIsChildSelected(isSelected);
            }else {
                list.get(i).getQueryCar().get(i).setIsChildSelected(false);
            }

        }
        isSelectAll = isSelectAllGroup(list);
        return isSelectAll;
    }

    /**
     * 勾与不勾选中选项
     *
     * @param isSelect 原先状态
     * @param ivCheck
     * @return 是否勾上，之后状态
     */
    public static boolean checkItem(boolean isSelect, ImageView ivCheck) {
        if (isSelect) {
            ivCheck.setImageResource(R.drawable.ic_checked);
        } else {
            ivCheck.setImageResource(R.drawable.ic_uncheck);
        }
        return isSelect;
    }

    /**=====================上面是界面改动部分，下面是数据变化部分=========================*/

    /**
     * 获取结算信息，肯定需要获取总价和数量，但是数据结构改变了，这里处理也要变；
     *
     * @return 0=选中的商品数量；1=选中的商品总价
     */
    public static String[] getShoppingCount(List<ShoppingCarBean> listGoods) {
        String[] infos = new String[3];
        String selectedCount = "0";//数量
        String selectedMoney = "0";//全部价格
        String selectedFright = "0";//运费
        String price = "0";
        String frightprice = "0";
        for (int i = 0; i < listGoods.size(); i++) {
            for (int j = 0; j < listGoods.get(i).getQueryCar().size(); j++) {
                boolean isSelectd = listGoods.get(i).getQueryCar().get(j).isChildSelected();
                if (isSelectd) {
                    price = listGoods.get(i).getQueryCar().get(j).getMoreGroPrice();
                    if (!TextUtils.isEmpty(listGoods.get(i).getQueryCar().get(j).getFreightPrice())){
                        frightprice = listGoods.get(i).getQueryCar().get(j).getFreightPrice();
                        Log.d("price--------",price+"-----"+frightprice);
                        price = String.valueOf(Double.parseDouble(price)+Double.parseDouble(frightprice));
                    }
                    String num = String.valueOf(listGoods.get(i).getQueryCar().get(j).getCount());
                    String countMoney = DecimalUtil.multiply(price, num);
                    String countFright = DecimalUtil.multiply(frightprice, num);
                    selectedMoney = DecimalUtil.add(selectedMoney, countMoney);
                    selectedFright = DecimalUtil.add(selectedFright, countFright);
                    selectedCount = DecimalUtil.add(selectedCount, "1");
                }
            }
        }
        infos[0] = selectedCount;
        infos[1] = selectedMoney;
        infos[2] = selectedFright;
        return infos;
    }


    public static boolean hasSelectedGoods(List<ShoppingCarBean> listGoods) {
        String count = getShoppingCount(listGoods)[0];
        return !"0".equals(count);
    }

    /**
     * 添加某商品的数量到数据库（非通用部分，都有这个动作，但是到底存什么，未可知）
     *
     * @param productID 此商品的规格ID
     * @param num       此商品的数量
     */
    public static void addGoodToCart(String productID, String num) {
        ShoppingCartDao.getInstance().saveShoppingInfo(productID, num);
    }

    /**
     * 删除某个商品,即删除其ProductID
     *
     * @param productID 规格ID
     */
    public static void delGood(String productID) {
        ShoppingCartDao.getInstance().deleteShoppingInfo(productID);
    }

    /** 删除全部商品 */
    public static void delAllGoods() {
        ShoppingCartDao.getInstance().delAllGoods();
    }

    /** 增减数量，操作通用，数据不通用 */
    public static void addOrReduceGoodsNum(boolean isPlus, GroupGoods goods, TextView tvNum) {
        String currentNum = String.valueOf(goods.getCount());
        String num = "1";
        if (isPlus) {
            num = String.valueOf(Integer.parseInt(currentNum) + 1);
        } else {
            int i = Integer.parseInt(currentNum);
            if (i > 1) {
                num = String.valueOf(i - 1);
            } else {
                num = "1";
            }
        }
        String productID = goods.getMoreGroId();
        tvNum.setText(num);
        goods.setCount(Integer.parseInt(num));
        updateGoodsNumber(productID, num);
    }

    /**
     * 更新购物车的单个商品数量
     *
     * @param productID
     * @param num
     */
    public static void updateGoodsNumber(String productID, String num) {
        ShoppingCartDao.getInstance().updateGoodsNum(productID, num);
    }

    /**
     * 查询购物车商品总数量
     * <p/>
     * 统一使用该接口，而就行是通过何种方式获取数据，数据库、SP、文件、网络，都可以
     *
     * @return
     */
    public static int getMoreGroupCount() {

        return ShoppingCartDao.getInstance().getGoodsCount();
      /*  int count = 0;
        for (int i = 0; i < list.size(); i++) {
            ShoppingCarBean scb = list.get(i);
            Log.d("====",""+scb.toString());
            if (scb == null) {
                continue;
            }
            count = scb.getCount();
            Log.d("====",""+count);
        }
        return count;*/
    }

    /**
     * 获取所有商品ID，用于向服务器请求数据（非通用部分）
     *
     * @return
     */
    public static List<String> getAllProductID() {
        return ShoppingCartDao.getInstance().getProductList();
    }

    /** 由于这次服务端没有保存商品数量，需要此步骤来处理数量（非通用部分） */
    public static void updateShopList(List<ShoppingCarBean> list) {
        if (list == null) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            ShoppingCarBean scb = list.get(i);
            if (scb == null) {
                continue;
            }
            List<GroupGoods> list2 = scb.getQueryCar();
            if (list2 == null) {
                continue;
            }
            for (int j = 0; j < list2.size(); j++) {
                GroupGoods goods = list2.get(j);
                if (goods == null) {
                    continue;
                }
                String productID = goods.getMoreGroId();
//                String num = ShoppingCartDao.getInstance().getNumByProductID(productID);
                int num = goods.getCount();
                list.get(i).getQueryCar().get(j).setCount(num);
            }
        }
    }

}
