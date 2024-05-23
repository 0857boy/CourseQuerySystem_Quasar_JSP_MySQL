<template>
  <q-page class="q-pa-md">
    <q-input v-model="course.id" placeholder="輸入課程 ID" />
    <q-input v-model="course.name" placeholder="輸入課程名稱" />
    <q-input v-model="course.description" placeholder="輸入課程描述" />
    <q-toolbar>
      <q-btn-group push>
        <q-btn @click="getCourse" label="查詢課程" />
        <q-btn @click="addCourse" label="新增課程" />
        <q-btn @click="deleteCourse" label="刪除課程" />
        <q-btn @click="updateCourse" label="修改課程" />
      </q-btn-group>
      <q-space />
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
          <q-td key="id">{{ props.row.id }}</q-td>
          <q-td key="name">{{ props.row.name }}</q-td>
          <q-td key="description">{{ props.row.description }}</q-td>
        </q-tr>
      </template>
    </q-table>
  </q-page>
</template>

<script>
import { course, courses, response, getCourse, addCourse, deleteCourse, updateCourse } from 'src/components/CourseService.js';
getCourse();
export default {
  setup() {
    return {
      course,
      courses,
      response,
      getCourse,
      addCourse,
      deleteCourse,
      updateCourse
    };
  },
};
</script>