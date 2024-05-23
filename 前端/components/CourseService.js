


import { ref, computed } from 'vue';
import axios from 'axios';
const baseURL = 'http://localhost:8087/CourseMagement_war_exploded';
let course = ref({
  id: '',
  name: '',
  description: '',
});
let courses = ref([]);
let response = ref(null);

const reset = () => {
  course.value = {
    id: '',
    name: '',
    description: '',
  };
};


const sortKey = ref('id'); // 預設排序的 key

const sortedCourses = computed(() => {
  return courses.value.slice().sort((a, b) => {
    return a[sortKey.value] > b[sortKey.value] ? 1 : -1;
  });
});

const getCourse = async () => {
  const id = course.value.id;
  response.value = '查詢課程等待回應中...';
  const command = 'getCourse';
  
  try {
    const res = await axios.get(`${baseURL}/api/courses/` , { params: {command : command, id: id} });
    console.log(res.data); // 檢查 API 回傳的資料
    courses.value = res.data; // 將 API 回傳的資料存入 courses
    courses.value = sortedCourses.value; // 以指定的 key 排序
    response.value = '查詢成功';
  } catch (error) {
    console.error(error); // 捕捉並輸出錯誤
    response.value = '查詢失敗';
  }
};

const addCourse = async () => {
  const { id, name, description } = course.value;

  if (!name || !description) {
    response.value = '請輸入課程名稱和描述';
    return;
  }

  response.value = '新增課程等待回應中...';

  const Data = {
    command: 'addCourse',
    id: id,
    name: name,
    description: description,
  };
  
  console.log(Data);

  const res = await axios.get(`${baseURL}/api/courses/` , { params: Data });
  response.value = res.data;
  reset();
  getCourse();
};


const deleteCourse = async () => {
  const id = course.value.id;

  response.value = '刪除課程等待回應中...';

  const Data = {
    command: 'deleteCourse',
    id: id,
  };
  const res = await axios.get(`${baseURL}/api/courses/` , { params: Data });
  response.value = res.data;
  reset();
  getCourse();
};

const updateCourse = async () => {
  const { id, name, description } = course.value;

  if (!name || !description) {
    response.value = '請輸入課程名稱和描述';
    return;
  }

  response.value = '修改課程等待回應中...';

  const Data = {
    command: 'updateCourse',
    id: id,
    name: name,
    description: description,
  };
  const res = await axios.get(`${baseURL}/api/courses/` , { params: Data });
  response.value = res.data;
  reset();
  getCourse();
};


export { course, courses, response, getCourse, addCourse, deleteCourse, updateCourse, sortKey, sortedCourses };

