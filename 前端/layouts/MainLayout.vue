<template>
  <q-route tab style="height: 100%">
    <q-layout view="lHh Lpr lFf">
      <q-header :style="{ backgroundColor: headerColor }">
        <q-toolbar>
          <q-toolbar-title>課程管理系統</q-toolbar-title>
          <q-btn color="primary" @click="goToOtherPage">切換頁面</q-btn>
        </q-toolbar>
      </q-header>
      
      <q-page-container>
        <router-view />
      </q-page-container>
    </q-layout>
  </q-route>
</template>

<script>
export default {
  data() {
    return {
      headerColor: 'primary'
    };
  },
  methods: {
    goToOtherPage() {
      if (this.$route.path == '/display') {
        this.$router.push('/');
        this.headerColor = '#2f81b8' ;
      } else {
        this.$router.push('/display');
        this.headerColor = '#1D1D1D';
      }
    }
  },
  
}
</script>