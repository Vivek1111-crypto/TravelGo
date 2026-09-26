import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    const publicEndpoints = ["/api/auth/login", "/api/auth/register"];

    if (publicEndpoints.includes(config.url)) {
      return config;
    }

    const accessToken = localStorage.getItem("accessToken");

    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;

      console.log("Authorization token attached");
    }

    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

export default api;
