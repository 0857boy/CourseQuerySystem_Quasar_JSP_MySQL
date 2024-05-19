<template>
  <div>
    <q-layout>
      <q-header>
        <q-toolbar>
          <q-toolbar-title>課程管理系統</q-toolbar-title>
        </q-toolbar>
      </q-header>

      <q-page-container>
        <q-page class="q-pa-md">
          <q-input v-model="course.id" placeholder="輸入課程 ID" />
          <q-input v-model="course.name" placeholder="輸入課程名稱" />
          <q-input v-model="course.description" placeholder="輸入課程描述" />
          <q-toolbar>
            <q-btn @click="getCourse" label="查詢課程" />
            <q-btn @click="addCourse" label="新增課程" />
            <q-btn @click="deleteCourse" label="刪除課程" />
            <q-btn @click="updateCourse" label="修改課程" />
            <q-space />
            <!-- 在這裡顯示發送請求後的回應 -->
            <div v-if="response">{{ response }}</div>
          </q-toolbar>
          

          <q-table 
          :rows="courses"
          :columns="['id', 'name', 'description']"
          >
          <template v-slot:top>
            <q-toolbar>
              <q-toolbar-title>課程列表</q-toolbar-title>
            </q-toolbar>
          </template>

          <template v-slot:body="props">
            <q-tr :props="props">
              <q-td key="id">{{ props
                .row.id }}</q-td>
              <q-td key="name">{{ props
                .row.name }}</q-td>
              <q-td key="description">{{ props
                .row.description }}</q-td>
            </q-tr>
          </template>
        </q-table>
        </q-page>
          <!-- 顯示所有課程的列表 -->
        

      </q-page-container>
    </q-layout>
      
  </div>
</template>

<script>
import axios from 'axios';
const baseURL = 'http://localhost:8087/CourseMagement_war_exploded';

export default {
  mounted() {
    this.getCourse();
  },
  data() {
    return {
      course: {
        id: '',
        name: '',
        description: '',
      },
      courses: [],
      response: null,  // 新增的數據屬性
    };
  },
  methods: {
    reset() {
      this.course = {
        id: '',
        name: '',
        description: '',
      };
    },
    getCourse() {
      const id = this.course.id;
      
      this.response = '查詢課程等待回應中...';
      const command = 'getCourse';
      
      axios.get(`${baseURL}/api/courses/` , { params: {command : command, id: id} })
        .then(response => {
          this.courses = response.data;
          this.response = '查詢成功';
        });
    },
    addCourse() {
      const { id, name, description } = this.course;


      if (!name || !description) {
        this.response = '請輸入課程名稱和描述';
        return;
      }

      this.response = '新增課程等待回應中...';

      const Data = {
        command: 'addCourse',
        id: id,
        name: name,
        description: description,
      };
      
      console.log(Data);

      axios.get(`${baseURL}/api/courses/` , { params: Data })
        .then(response => {
          this.response = response.data;
          this.reset();
          this.getCourse();
        });

      
    },
    deleteCourse() {
      const id = this.course.id;


      this.response = '刪除課程等待回應中...';

      const Data = {
        command: 'deleteCourse',
        id: id,
      };
      axios.get(`${baseURL}/api/courses/` , { params: Data })
        .then(response => {
          this.response = response.data;
          this.reset();
          this.getCourse();
        });
    },
    updateCourse() {
      const { id, name, description } = this.course;

      if (!name || !description) {
        this.response = '請輸入課程名稱和描述';
        return;
      }

      this.response = '修改課程等待回應中...';

      const Data = {
        command: 'updateCourse',
        id: id,
        name: name,
        description: description,
      };
      axios.get(`${baseURL}/api/courses/` , { params: Data })
        .then(response => {
          this.response = response.data;
          this.reset();
          this.getCourse();
        });
    },
  },
};
</script>