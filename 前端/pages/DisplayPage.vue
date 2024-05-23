<template>
    <q-btn-dropdown label="Sort by" icon="sort" align="right">
        <q-item clickable v-ripple @click="SortbyId">ID</q-item>
        <q-item clickable v-ripple @click="SortbyName">Name</q-item>
        <q-item clickable v-ripple @click="SortbyDescription">Description</q-item>

    </q-btn-dropdown>

    <q-page class="flex flex-center">
        <q-table
        :rows="courses"
        :columns="['id', 'name', 'description']"
        v-if="courses" >
        <template v-slot:body="props">
            <q-tr>
            <q-td key="id">{{ props.row.id }}</q-td>
            <q-td key="name">{{ props.row.name }}</q-td>
            <q-td key="description">{{ props.row.description }}</q-td>
            </q-tr>
        </template>
        </q-table>
    </q-page>
</template>

<script setup>
import { onUnmounted } from 'vue';
import { courses, getCourse, sortKey } from 'src/components/CourseService.js';

let intervalId = setInterval(getCourse, 100);

onUnmounted(() => {
  clearInterval(intervalId);
});

function SortbyId() {
  sortKey.value = 'id';
  getCourse();
}

function SortbyName() {
  sortKey.value = 'name';
  getCourse();
}

function SortbyDescription() {
  sortKey.value = 'description';
  getCourse();
}

</script>

