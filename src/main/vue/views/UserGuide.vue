<template>
    <div id="app">
        <Header class="top-header"></Header>
        <BaseHeading class="base-header" name=""></BaseHeading>
        <h2 class="h2-user-guide"> {{ $t('UserGuide.name') }} </h2>

        <div class="generalInfo">
            <h2 class="h2-general-info">{{ $t('UserGuide.section-1') }}</h2>
            <b-col class="col-for-mobile">
                <Accordion
                    v-for="(generalInfo, key) in testInfos"
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
            <h2 class="h2-general-info">Signieren</h2>
            <b-col class="col-for-mobile">
                <Accordion
                    v-for="(generalInfo, key) in testInfos2"
                    :general-info="generalInfo"
                    :index="key"
                    :key="key"
                    :section="1"
                    :open="generalInfo.open"
                    @toggleOpen="toggleOpen2"
                />
            </b-col>
        </div>

        <div class="generalInfo" style="margin-bottom: 10em">
            <h2 class="h2-general-info">Dokumente</h2>
            <b-col class="col-for-mobile">
                <Accordion
                    v-for="(generalInfo, key) in testInfos3"
                    :general-info="generalInfo"
                    :index="key"
                    :key="key"
                    :section="2"
                    :open="generalInfo.open"
                    @toggleOpen="toggleOpen3"
                />
            </b-col>
        </div>


    </div>
</template>

<script>
import Accordion from "@/main/vue/components/Accordion";
import Header from "@/main/vue/components/header/Header";
export default {
    name: "UserGuide",
    components: {Header, Accordion},
    data: function () {
        return {

            testInfos: [
                {
                    key: 0,
                    open: false,
                    question: this.$t('UserGuide.test.0'),
                    answer: "TEST0"
                },
                {
                    key: 1,
                    open: false,
                    question: this.$t('UserGuide.test.1'),
                    answer: "TEST1"
                    }
                ],
            testInfos2: [
                {
                    key: 0,
                    open: false,
                    question: this.$t('UserGuide.test.0'),
                    answer: "TEST0"
                },
                {
                    key: 1,
                    open: false,
                    question: this.$t('UserGuide.test.1'),
                    answer: "TEST1"
                }
            ],
            testInfos3: [
                {
                    key: 0,
                    open: false,
                    question: this.$t('UserGuide.test.0'),
                    answer: "TEST0"
                },
                {
                    key: 1,
                    question: this.$t('UserGuide.test.1'),
                    answer: "TEST1"
                }
            ]
            }
        },
    methods: {
        // perhaps find a another method to use one method for the each data


        toggleOpen: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.testInfos = this.testInfos.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;

                } else {
                    generalInfo.open = false;
                }
                return generalInfo;
            });

        },
        toggleOpen2: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.testInfos2 = this.testInfos2.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;

                } else {
                    generalInfo.open = false;
                }
                return generalInfo;
            });

        },
        toggleOpen3: function (index) {
            // iterate through list and set the open boolean to true or false by considering the clicked index
            this.testInfos3 = this.testInfos3.map((generalInfo, i) => {
                if (index === i) {
                    generalInfo.open = !generalInfo.open;

                } else {
                    generalInfo.open = false;
                }
                return generalInfo;
            });

        }
    }
}
</script>

<style>

.base-header {
    margin-top: 2.2em;
}

.h2-user-guide {
    display: flex;
    align-items: center;
    margin-top: 3em;
    font-size: 2em;
    margin-left: 5rem;
    text-align: left;
}
/* for the line after the headline */
.h2-user-guide::after {
    content: '';
    flex: 1;
    margin-left: .5rem;
    height: 1px;
    width: 100%;
    background-color: #000;
}
.h2-general-info {
    margin-left: 3.8em;
    font-size: 1.6em;
    margin-top: 1.2em;
    color: #436495;
}

.generalInfo {
    text-align: left;
}

.generalInfo .question .h2-head {
    font-size: .8em;
    text-align: left;
    margin-left: 10em;
    margin-top: -20px;
}

.generalInfo .open .question .ui-icon-caret-1-n {
    transform: translateY(30%) rotate(90deg);
    color: #436495;
    width: 15px;
    height: 15px;
}

body {
    background-color: #EEE;
    font-family: sans-serif;
}

.generalInfo {
    width: 100%;
    max-width: 768px;
    background: white;
}
.generalInfo .question {
    alignment: left;
    color: #3c3c3c;
    font-size: 30px;
    margin-top: -1em;
    transition: all 0.4s linear;
    margin-left: -1.3em;
    /*cursor: pointer;*/
}

.generalInfo .open .question {
    margin-bottom: 0px;
}

.generalInfo .answer {
    color: #3c3c3c;
    font-size: 18px;
    margin-left: 15.5rem;
    text-align: left;
    opacity: 0;
    max-height: 0;
    overflow-y: hidden;
    /*transition: all 0.5s ease-in-out;*/
}
.generalInfo .open .answer {
    opacity: 1;
    max-height: 1000px;
}

/* for screen width which are equal 500px or less*/
@media screen and (max-width: 500px) {
    /* For the Right Arrow (Go Back arrow)*/
    .base-header {
        margin-top: 4.3em;
    }

    .top-header {
        height: 7%;
    }

    /* for user guide headers */
    .h2-user-guide {
        margin-top: .8em;
        margin-left: 2em;
        font-size: 1.7em;
    }

    .generalInfo {
        margin-left: -1.4em;
    }


    /* for faq section (general infos) */
    .h2-general-info {
        font-size: 1.3em;
        margin-left: 4.4em;
        margin-top: 1.6em;
    }
    /* for the faq section (signaure) */
    .generalInfo .header-section-2 {
        font-size: 1.3em;
        margin-left: 3.4em;

    }
    /* for faq headers */
    .generalInfo .question{
        font-size: 1.7em;
        margin-left: -4em;
        transition: top 4s ease 0s;

    }
    /* for faq answer of the headers */
    .generalInfo .answer {
        font-size: 1em;
        margin-left: 10em;
        overflow-y: hidden;
        text-align: left;
        opacity: 0;
        max-height: 0;
        transition: top 4s ease 0s;
    }
    /* for faq answer for the open cause (collapsed) */
    .generalInfo .open .answer {
        opacity: 1;
        font-size: .9em;
        max-height: 1000px;
        margin-left: 7em;
        transform: translateY(-10%);

    }
    /* for the icon in the faq header */
    .generalInfo .question .ui-icon-caret-1-n {
        content: '';
        margin-left: 7em;
        transform: translateY(0%) rotate(0deg);
        width: 10px;
        height: 10px;
        color: #436495;
    }
    /* for the icon in the faq header open cause (collapsed) */
    .generalInfo .open .question .ui-icon-caret-1-n {
        transform: translateY(5%) rotate(90deg);
        color: #436495;
        width: 10px;
        height: 10px;
    }
}

/* for screen width of 550px or greater than 550px */
@media screen and (min-width: 501px ) {
    .top-header {
        height: 10%;
    }
    .base-header {
        margin-top: 3em;
    }
    .h2-user-guide {
        margin-top: 1em;
    }
    .generalInfo .col-for-mobile {
        margin-left: -3em;
    }
    .generalInfo .header-section-2 {
        font-size: 1.6em;
        margin-left: 5.2em;

    }
    .generalInfo .question {
        font-size: 1.5em;
    }
    .generalInfo .question .ui-icon-caret-1-n {
        content: '';
        margin-left: 7em;
        transform: translateY(30%) rotate(0deg);
        width: 15px;
        height: 15px;
        color: #436495;
    }
    .generalInfo .open .answer {
        margin-left: 11.4em;
        font-size: .9em;
    }
}
</style>
