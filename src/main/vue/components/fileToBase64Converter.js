

/* example Usage:

       async asyncHandleFunction() {
          return await convertUploadFileToBase64(file)     <- your selected file which is going to be uploaded
        },

        and this you only have to save into the string e.g:
            documentObject.data = await asyncHandleFunction

 */

// converting the selected file into base64 (as promise)
export const convertUploadFileToBase64 = (file) => {
    const reader = new FileReader()
    return new Promise((resolve, reject) => {
        reader.onerror = (error) => {
            reader.abort()
            reject(error)
        }
        reader.onload = () => {
            resolve(reader.result.replace('data:', '').replace(/^.+,/, ''))
        }
        reader.readAsDataURL(file)
    })
}
