<template>
  <div class="home">
    <van-row>
    <van-col span="24">
      <van-tabs @click="onClick"  title-active-color="#E32DAB" color="#E32DAB"  :line-width="100" :line-height="2">
        <van-tab  v-for="index in categories.length" :title="categories[index-1].name">
            <van-card v-for="(item,index) in phones"
              :price="item.price"
              :desc="item.desc"
              :title="item.title"
              :thumb="item.thumb">
              <template #tags>
                <van-tag v-for="tag in item.tag" color="#f2826a" style="margin-left: 5px;">{{tag.name}}</van-tag>
              </template>
              <template #footer>
                 <van-button round type="info" size="mini" @click="buy(index)">购买</van-button>
              </template>
            </van-card>
        </van-tab>
      </van-tabs>
      </van-col>
    </van-row>

    <van-sku
            v-model="show"
            :sku="sku"
            :goods="goods"
            :hide-stock="sku.hide_stock"
            @buy-clicked="onBuyClicked"
    >
      <template #sku-actions="props">
        <div class="van-sku-actions">

          <!-- 直接触发 sku 内部事件，通过内部事件执行 onBuyClicked 回调 -->
          <van-button
                  square
                  size="large"
                  type="danger"
                  @click="props.skuEventBus.$emit('sku:buy')"
          >
            买买买
          </van-button>
        </div>
      </template>
    </van-sku>
  </div>
</template>

<script>
  export default {
    data() {
      return {
          categories: [
              {
                  name: "魅焰红",
                  type: 1
              },
              {
                  name: "极光蓝",
                  type: 1
              },
              {
                  name: "优雅黑",
                  type: 1
              },
              {
                  name: "铂金光",
                  type: 1
              }
          ],
        phones: [
          {
            id: 1,
            title: "Honor 8A",
            price: "2800.00",
            desc: "魅焰红",
            tag: [
              {
                name: "720P珍珠屏"
              },
              {
                name: "Micro USB接口"
              }
            ],
            thumb: "../static/e84a2e03-7f19-41d2-98a5-a5c16b7e252d.jpg"
          }
        ],
        show: false,
        goods: {
              picture: "../static/e84a2e03-7f19-41d2-98a5-a5c16b7e252d.jpg"
          },
        sku: {
              tree: [
                  {
                      k: "规格",
                      v: [
                          {
                              id: 1,
                              name: "32GB",
                              imgUrl: "../static/e84a2e03-7f19-41d2-98a5-a5c16b7e252d.jpg",
                              previewImgUrl: "../static/e84a2e03-7f19-41d2-98a5-a5c16b7e252d.jpg"
                          },
                          {
                              id: 2,
                              name: "64GB",
                              imgUrl: "../static/e84a2e03-7f19-41d2-98a5-a5c16b7e252d.jpg",
                              previewImgUrl: "../static/e84a2e03-7f19-41d2-98a5-a5c16b7e252d.jpg"
                          }
                      ],
                      k_s: "s1"
                  }
              ],
              list: [
                  {
                      s1: 1,
                      price: 280000,
                      stock_num:3
                  },
                  {
                      s1: 2,
                      price: 320000,
                      stock_num: 3
                  }
              ],
              price: "2800.00",
              stock_num: 2,
              none_sku: false,
              hide_stock: false
          }
      };
    },
    created(){
        const _this=this;
        axios.get("http://localhost:8081/phone/index/").then(function (resp) {
            // console.log(resp.data);
            _this.categories=resp.data.categories;
            _this.phones=resp.data.phones
        })
    },
    methods:{
        onClick(index){
          const _this=this;
          axios.get("http://localhost:8081/phone/findByCategroyType/"+_this.categories[index].type).then(function (resp) {
              // console.log(resp.data)
              _this.phones=resp.data;
          })
        },
        buy(index){
            this.show=true;
            const _this=this;
            axios.get("http://localhost:8081/phone/findSpecsByPhoneId/"+_this.phones[index].id).then(function (resp) {
                // console.log(resp.data)
                _this.goods=resp.data.data.goods;
                _this.sku=resp.data.data.sku;
            })
        },
        onBuyClicked(item){
            this.$store.state.specsId = item.selectedSkuComb.s1;
            this.$store.state.quantity = item.selectedNum;
            this.$router.push('/addressList')
        }
    }
  };
</script>