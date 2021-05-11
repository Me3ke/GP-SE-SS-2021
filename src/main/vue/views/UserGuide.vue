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
                    question: this.$t('UserGuide.section0.0'),
                    answer: "TEST0"
                },
                {
                    key: 1,
                    open: false,
                    question: this.$t('UserGuide.section0.1'),
                    answer: "TEST1"
                    }
                ],
            testInfos2: [
                {
                    key: 0,
                    open: false,
                    question: this.$t('UserGuide.section1.0'),
                    answer: "TEST0"
                },
                {
                    key: 1,
                    open: false,
                    question: this.$t('UserGuide.section1.1'),
                    answer: "TEST1"
                }
            ],
            testInfos3: [
                {
                    key: 0,
                    open: false,
                    question: this.$t('UserGuide.section2.0'),
                    answer: "TEST0"
                },
                {
                    key: 1,
                    question: this.$t('UserGuide.section2.1'),
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
body {
    background-color: #EEE;
    font-family: sans-serif;
}

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
/* for the Section and icons(arrows) */
.generalInfo {
    text-align: left;
    width: 100%;
    max-width: 768px;
    background: white;
}
/* For the faq question header */
.generalInfo .question .h2-head {
    font-size: .8em;
    text-align: left;
    margin-left: 1.2em;
    margin-top: -20px;
}
/* for the faq question*/
.generalInfo .question {
    position: relative ;
    color: #3c3c3c;
    width: 99%;
    left: 7em;
    font-size: 30px;
    margin-top: -1em;
    transition: all 0.4s linear;
    margin-left: 20em;
    cursor: pointer;
}
/* for the arrow icon */

/* standard */
.generalInfo .question .ui-icon-caret-1-n {
    margin-left: 40em;
}
/* open case */
.generalInfo .open .question .ui-icon-caret-1-n {
    transform: translateY(30%) rotate(90deg);
    color: #436495;
    width: 15px;
    height: 15px;
}

.generalInfo .open .question {
    margin-bottom: 0;
}

/* For the answer of the faq question*/

/* standard */
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
/* open cause*/
.generalInfo .open .answer {
    opacity: 1;
    max-height: 1000px;
}
.wrapper {
    padding-bottom: 0;
    margin-left: 10em;
    border: 1px solid rgba(0, 0, 0, 0.125);
    border-radius: 0.25rem;
}

/* for screen width which are equal 500px or less*/
/*
@media screen and (max-width: 500px) {
    /* For the Right Arrow (Go Back arrow)*/
/*
    .base-header {
        margin-top: 4.3em;
    }

    .top-header {
        height: 7%;
    }

    /* for user guide headers */
/*
    .h2-user-guide {
        margin-top: .8em;
        margin-left: 2em;
        font-size: 1.7em;
    }

    .generalInfo {
        margin-left: -1.4em;
    }


    /* for faq section (general infos) */
/*
    .h2-general-info {
        font-size: 1.3em;
        margin-left: 4.4em;
        margin-top: 1.6em;
    }
    /* for the faq section (signaure) */
/*
    .generalInfo .header-section-2 {
        font-size: 1.3em;
        margin-left: 3.4em;

    }
    /* for faq headers */
/*
    .generalInfo .question{
        font-size: 1.7em;
        margin-left: -4em;
        transition: top 4s ease 0s;

    }
    /* for faq answer of the headers */
/*
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
/*
    .generalInfo .open .answer {
        opacity: 1;
        font-size: .9em;
        max-height: 1000px;
        margin-left: 7em;
        transform: translateY(-10%);

    }
    /* for the icon in the faq header */
/*
    .generalInfo .question .ui-icon-caret-1-n {
        content: '';
        transform: translateX(20%) translateY(0%) rotate(0deg);
        width: 10px;
        height: 10px;
        color: #436495;
    }
    /* for the icon in the faq header open cause (collapsed) */
/*
    .generalInfo .open .question .ui-icon-caret-1-n {
        transform: translateY(5%) rotate(90deg);
        color: #436495;
        width: 10px;
        height: 10px;
    }
}*/

/* for screen width of 550px or greater than 550px */
/*@media screen and (min-width: 501px ) {
    .top-header {
        height: 10%;
    }
    .base-header {
        margin-top: 3em;
    }
    .h2-user-guide {
        margin-top: .5em;
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
    .wrapper {
        margin-left: 10em;
        margin-top: -1em;
        width: 80%;
    }
    .generalInfo .question .ui-icon-caret-1-n {
        content: '';
        margin-left: 0em;
        transform: translateY(30%) rotate(0deg);
        width: 15px;
        height: 15px;
        color: #436495;
    }
    .generalInfo .open .answer {
        margin-left: 11.4em;
        font-size: .9em;
    }
}*/

@media screen and (max-width: 768px) {
    .generalInfo .question .h2-head {
        font-size: .5em;
        margin-top: -2.7em;
    }
    .generalInfo {
        margin-left: 10em;
    }


    .generalInfo .open .answer {
        font-size: .5em;
    }
    .h2-user-guide {
        font-size: 1.6em;
        margin-top: .5em;
    }
    .h2-general-info {
        font-size: 1em;
        margin-left: -6em;
    }

    .generalInfo {
        padding-bottom: 0em;
    }
    .wrapper {
        margin-left: -2.9em;
        margin-top: -1em;
        width: 60%;
    }

    /* for the arrow icon */

    /* standard */
    .generalInfo .question .ui-icon-caret-1-n {
        transform: translateY(-160%);
    }

    /* open case */
    .generalInfo .open .question .ui-icon-caret-1-n {
        transform: translateY(-160%) rotate(90deg);
        color: #436495;
        width: 15px;
        height: 15px;
    }


}

@media screen and (max-width: 440px) {
    .generalInfo .question .h2-head {
        font-size: .8em;
        margin-top: -1.8em;
    }
    .generalInfo {
        margin-left: 10em;
    }

    .generalInfo .open .answer {
        font-size: 1.2em !important;
        margin-left: 2em !important;
    }
    .h2-user-guide {
        font-size: 2.4em;
        margin-top: .3em;
        margin-left: 1em;
    }
    .h2-general-info {
        font-size: 2em;
        margin-left: -3.5em;
    }

    .generalInfo {
        padding-bottom: 0em;
    }
    .wrapper {
        margin-left: -10em;
        margin-top: -1em;
        width: 100%;
    }

    /* for the arrow icon */

    /* standard */
    .generalInfo .question .ui-icon-caret-1-n {
        transform: translateY(-54%);
        margin-left: 0;
        width: 20px;
        height: 20px;
        color: #436495 ;

    }

    /* open case */
    .generalInfo .open .question .ui-icon-caret-1-n {
        transform: translateY(-50%) rotate(90deg);
        width: 20px;
        height: 20px;
    }
}

</style>
