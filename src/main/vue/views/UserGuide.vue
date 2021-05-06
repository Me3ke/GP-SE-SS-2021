<template>
    <div>
        <Header></Header>
        <b-container>
            <BaseHeading name=""></BaseHeading>
            <h2> {{ $t('UserGuide.name') }} </h2>
            <b-container id="general-information">
                <b-row>
                    <h4 style="font-size: 26px; margin-left: 18px; margin-top: 5px; margin-bottom: -20px; color: #436495"> Allgemeine Information</h4>
                </b-row>
                <b-row>
                    <div class="collapse-block">
                        <input id="collapsible" class="arrow-toggle" type="checkbox">
                        <label for="collapsible" class="label-collapsible-header"> Was ist Elsa? </label>
                        <div class="collapsible-content">
                            <div class="content-inner">
                                <p style="margin-left: 20px">
                                    QUnit is by calling one of the object that are embedded in JavaScript
                                </p>
                            </div>
                        </div>
                    </div>
                </b-row>
                <b-row>
                    <p style="margin-left: 20px; margin-top: -8px">Wie funktioniert Elsa?</p>
                </b-row>
            </b-container>

        </b-container>

    </div>
</template>

<script>
import Header from "@/main/vue/components/Header";
export default {
    name: "UserGuide",
    components: {Header}
}
</script>

<style scoped>
@import '/src/main/vue/assets/css/global.css';


h2 {
    display: flex;
    align-items: center;
    margin-top: 100px;
    font-size: 40px;
}
/* for the line after the headline */
h2::after {
    content: '';
    flex: 1;
    margin-left: .5rem;
    height: 1px;
    background-color: #000;
}


input[type='checkbox']
    { display: none;
}
.collapse-block
    {
    margin: 1.2rem 1px 10px 2rem;
    }
.label-collapsible-header
    {
    display: block;
    font-size: 19px;
    text-align: left;
    padding: .5rem;
    margin-bottom: -4px;
    color: black;
    background: white;
    cursor: pointer;
    border-radius: 5px;
    transition: all 0.25s ease-out;
    }
.label-collapsible-header:hover
    {
    color: blue;
    }
.label-collapsible-header::before
    {
    content: ' ';
    display: inline-block;
    border-top: 5px solid transparent;
    border-bottom: 5px solid transparent;
    border-left: 5px solid currentColor;
    vertical-align: middle;
    margin-right: .7rem;
    transform: translateY(-2px);
    transition: transform .2s ease-out;
}


/* rotating the arrow toogle */
.arrow-toggle:checked+.label-collapsible-header::before
{
    transform: rotate(90deg) translateX(-3px);
}
.arrow-toggle:checked + .label-collapsible-header + .collapsible-content
    {
    max-height: 100px;
    }
.arrow-toggle:checked+.label-collapsible-header
    {
    border-bottom-right-radius: 0;
    border-bottom-left-radius: 0;
    }

/* collapsible innter backgorund,border*/
.collapsible-content .content-inner
    {
    background: white;
    }

/* for the content div
transition for the time of the collapsing */
.collapsible-content
{
    max-height: 0;
    margin-left: .5em;
    overflow: hidden;
    transition: max-height .30s ease-in-out;
}
/* Here is the Style settings for the content paragraph(p) of the collapsible*/
.collapsible-content p
    {
    font-size: 12px;
    }
</style>
