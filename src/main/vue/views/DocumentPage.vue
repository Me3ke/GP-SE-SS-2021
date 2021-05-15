<template>
    <div>
        <Header></Header>
        <BaseHeading :name="document.title" style="position: fixed"></BaseHeading>

        <PDFViewer :pdf-src=src></PDFViewer>

        <Footer></Footer>
    </div>
</template>

<script>
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";
import PDFViewer from "@/main/vue/components/pdfViewer/PDFViewer";


export default {
    name: "DocumentPage",
    props: {
        docId: {
            type: [Number, String]
        },
        envId: {
            type: [Number, String]
        },
        userId: {
            type: [Number, String]
        }
    },
    components: {PDFViewer, Footer, Header},
    data() {
        return {
            document: {
                "title": "Ich bin ein Dokument",
                "state": "open",
                "owner": {
                    "e-mail": "superMail@mailService.de",
                    "firstname": "Samuel",
                    "lastname": "Ignatory",
                },
                "creationDate": "12.04.2021",
                "endDate": "23.05.2021",
                "signatureType": "simple",
                "datatype": "PDF",
                "signatory": "True",
                "signed": "False",
                "reader": "False",
                "read": "False",
                "data": ""
            },
            pdf: undefined,
            pages: [],
            src: "https://cdn.filestackcontent.com/5qOCEpKzQldoRsVatUPS"
        }
    }

}
</script>

<style scoped>

</style>
