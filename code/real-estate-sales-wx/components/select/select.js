Component({
  /**
   * 组件的属性列表
   */
  properties: {
    selectData: { //下拉列表的数据
      type: Array,
      value: [] //初始数据,可通过属性修改
    },
  },

  /**
   * 组件的初始数据
   */
  data: {
    showName:'请选择',
    selectShow: false, //控制下拉列表的显示隐藏，false隐藏、true显示
    currentIndex: 0, //选择的下拉列表下标
  },

  /**
   * 组件的方法列表
   */
  methods: {
    selectTap() {
      this.setData({
        selectShow: !this.data.selectShow
      });
    },
    optionTap(e) {
      let Index = e.currentTarget.dataset.index;
      this.setData({
        currentIndex: Index,
        selectShow: !this.data.selectShow,
        showName: e.currentTarget.dataset.title
      });
      this.triggerEvent('selected', { value: e.currentTarget.dataset.value });
    }
  }
})