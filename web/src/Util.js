import adios1 from 'axios'
let instance = adios1.create({
    headers: {
      //  'content-type': 'application/x-www-form-urlencoded'
    }
})
instance.defaults.timeout = 8000;
instance.interceptors.request.use(
    config => {
           config.baseURL = "/pms11/"
        // config.baseURL = "http://localhost:9527/"
       /* if(sessionStorage.username){
            config.params={
                "username": sessionStorage.username,
                "userid": sessionStorage.userid,
                ...config.params
            }
        }*/
        return config;
    },
    err => {
        return Promise.reject(err);
    });
// http response 拦截器
instance.interceptors.response.use(
    response => {
        return response.data
    },
    error => {
        return Promise.reject(error.response.status) // 返回接口返回的错误信息
    })
export default instance




