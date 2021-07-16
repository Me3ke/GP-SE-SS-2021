<template>
    <div id="app" style="background-color: var(--whitesmoke); height: 100vh; overflow: hidden">
        <Header class="top-header"></Header>
        <BaseHeading class="base-header" name=""></BaseHeading>
        <h2 class="h2-user-guide"> {{ $t('UserGuide.name') }} </h2>

        <div class="overflow" style="background-color: var(--whitesmoke); height: 78.5vh;">
            <div class="generalInfo">
                <h2 class="h2-general-info">{{ $t('UserGuide.section-1') }}</h2>
                <b-col class="col-for-mobile">
                    <Accordion
                        v-for="(generalInfo, key) in sectionData"
                        :general-info="generalInfo"
                        :index="key"
                        :key="key"
                        :section="0"
                        :open="generalInfo.open"
                        @toggleOpen="toggleOpen"
                    />
                </b-col>
            </div>
            <div class="generalInfo">
                <h2 class="h2-general-info">{{ $t('UserGuide.section-2') }}</h2>
                <b-col class="col-for-mobile">
                    <Accordion
                        v-for="(generalInfo, key) in sectionData2"
                        :general-info="generalInfo"
                        :index="key"
                        :key="key"
                        :section="1"
                        :open="generalInfo.open"
                        @toggleOpen="toggleOpen2"
                    />
                </b-col>
            </div>

            <div class="generalInfo">
                <h2 class="h2-general-info">{{ $t('UserGuide.section-3') }}</h2>
                <b-col class="col-for-mobile">
                    <Accordion
                        v-for="(generalInfo, key) in sectionData3"
                        :general-info="generalInfo"
                        :index="key"
                        :key="key"
                        :section="2"
                        :open="generalInfo.open"
                        @toggleOpen="toggleOpen3"
                    />
                </b-col>
            </div>

            <div class="generalInfo">
                <h2 class="h2-general-info"> {{ $t('UserGuide.section-4') }} </h2>
                <b-col class="col-for-mobile">
                    <Accordion
                        v-for="(generalInfo, key) in sectionData4"
                        :general-info="generalInfo"
                        :index="key"
                        :key="key"
                        :section="3"
                        :open="generalInfo.open"
                        @toggleOpen="toggleOpen4"
                    />
                </b-col>
            </div>

            <div class="generalInfo">
                <h2 class="h2-general-info"> {{ $t('UserGuide.section-5') }} </h2>
                <b-col class="col-for-mobile">
                    <Accordion
                        v-for="(generalInfo, key) in sectionData5"
                        :general-info="generalInfo"
                        :index="key"
                        :key="key"
                        :section="4"
                        :open="generalInfo.open"
                        @toggleOpen="toggleOpen5"
                    />
                </b-col>
            </div>

            <div class="generalInfo" style="margin-bottom: 10em">
                <h2 class="h2-general-info"> {{ $t('UserGuide.section-6') }} </h2>
                <b-col class="col-for-mobile">
                    <Accordion
                        v-for="(generalInfo, key) in sectionData6"
                        :general-info="generalInfo"
                        :index="key"
                        :key="key"
                        :section="5"
                        :open="generalInfo.open"
                        @toggleOpen="toggleOpen6"
                    />
                </b-col>
            </div>

        </div>
        <Footer></Footer>
    </div>
</template>

<script>
import Accordion from "@/main/vue/components/Accordion";
import Header from "@/main/vue/components/header/Header";
import Footer from "@/main/vue/components/Footer";

export default {
    name: "UserGuide",
    components: {Footer, Header, Accordion},
    data: function () {
        return {
            // General Information
            sectionData: [
                {
                    key: 0,
                    open: false,
                    img: null,
                },
                {
                    key: 1,
                    open: false,
                    img: null,
                }
            ],
            // Account Management
            sectionData2: [
                {
                    key: 0,
                    open: false,
                    img: null,
                },
                {
                    key: 1,
                    open: false,
                    img: null,
                },
                {
                    key: 2,
                    open: false,
                    img: null,
                },
                {
                    key: 3,
                    open: false,
                    img: null,
                },
                {
                    key: 4,
                    open: false,
                    img: null,
                },
                {
                    key: 5,
                    open: false,
                    img: null,
                }
            ],
            // Signature
            sectionData3: [
                {
                    key: 0,
                    open: false,
                    img: null,
                },
                {
                    key: 1,
                    open: false,
                    img: null,
                },
                {
                    key: 2,
                    open: false,
                    img: null,
                },
                {
                    key: 3,
                    open: false,
                    img: null,
                },
            ],
            // Documents
            sectionData4: [
                {
                    key: 0,
                    open: false,
                    img: null,
                },
                {
                    key: 1,
                    open: false,
                    img: null,
                },
                {
                    key: 2,
                    open: false,
                    img: null,
                },
                {
                    key: 3,
                    open: false,
                    img: null,
                },
                {
                    key: 4,
                    open: false,
                    img: null,
                },
                {
                    key: 5,
                    open: false,
                    img: null,
                },
                {
                    key: 6,
                    open: false,
                    img: null,
                },
                {
                    key: 7,
                    open: false,
                    img: null,
                },
                {
                    key: 8,
                    open: false,
                    img: null,
                },
                {
                    key: 9,
                    open: false,
                    img: null,
                }
            ],
            // filter and search settings
            sectionData5: [
                {
                    key: 0,
                    open: false,
                    img: null,
                },
                {
                    key: 1,
                    open: false,
                    img: null,
                },
                {
                    key: 2,
                    open: false,
                    img: null,
                },
                // Sort my documents
                {
                    key: 3,
                    open: false,
                    img: null,
                }
            ],
            // admin
            sectionData6: [
                {
                    key: 0,
                    open: false,
                    img: null,
                },
                {
                    key: 1,
                    open: false,
                    img: null,
                },
                {
                    key: 2,
                    open: false,
                    img: null,
                }
                ]
        }
    },
    methods: {
        // perhaps find a another method to use one method for the each data


        toggleOpen: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.sectionData = this.sectionData.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;
                }
                return generalInfo;
            });

        },
        toggleOpen2: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.sectionData2 = this.sectionData2.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;
                }
                return generalInfo;
            });

        },
        toggleOpen3: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.sectionData3 = this.sectionData3.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;
                }
                return generalInfo;
            });

        },
        toggleOpen4: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.sectionData4 = this.sectionData4.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;
                }
                return generalInfo;
            });

        },
        toggleOpen5: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.sectionData5 = this.sectionData5.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;
                }
                return generalInfo;
            });
        },

        toggleOpen6: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.sectionData6 = this.sectionData6.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;
                }
                return generalInfo;
            });
        }
    }
}
</script>

<style src="../assets/css/userguide.css">

</style>
