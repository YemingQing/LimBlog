<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <p>Designed by
        <strong>
          <router-link to="/" class="me-login-design-color">Limerenxey</router-link>
        </strong>
      </p>
      <div style="font:12px Tahoma; margin:0px auto; text-align:center;">
      			<div>
      			<hr/>
      			Copyright &copy;&nbsp;&nbsp;2020-2021&nbsp;
      			<a href="https://mq.forlearn.top">Limerenxey's blog</a> ALL RIGHT RESERVED<br/>
      			联系方式：mingq0110@163.com<br/>
      			</div>
      </div>
    </div>
  </el-footer>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {},
    mounted() {
    }
  }
</script>

<style>

  .el-footer {
    min-width: 100%;
    box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 1024;
  }

  .me-footer {
    text-align: center;
    line-height: 60px;
    font-family: 'Open Sans', sans-serif;
    font-size: 18px;

  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>
