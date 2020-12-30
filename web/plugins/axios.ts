export default function ({$axios, error: nuxtError}) {
  $axios.onError(error => {
    nuxtError({
      data: error.response.data,
      statusCode: error.response.status,
      message: error.message,
    });
    return Promise.resolve(false);
  })
}
