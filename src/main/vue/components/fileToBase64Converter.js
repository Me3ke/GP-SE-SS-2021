

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
